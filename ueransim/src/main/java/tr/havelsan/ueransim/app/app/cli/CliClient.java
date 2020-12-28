package tr.havelsan.ueransim.app.app.cli;

import picocli.CommandLine;
import tr.havelsan.ueransim.app.common.GnbId;
import tr.havelsan.ueransim.app.common.Supi;
import tr.havelsan.ueransim.app.common.cli.*;
import tr.havelsan.ueransim.app.common.nts.IwPerformCycle;
import tr.havelsan.ueransim.nts.nts.NtsTask;
import tr.havelsan.ueransim.utils.Constants;
import tr.havelsan.ueransim.utils.Pair;
import tr.havelsan.ueransim.utils.Utils;
import tr.havelsan.ueransim.utils.jcolor.AnsiColorFormat;
import tr.havelsan.ueransim.utils.jcolor.AnsiPalette;
import tr.havelsan.ueransim.utils.octets.OctetString;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class CliClient {
    private static final Object SOCKET_CLOSE = new Object();

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private MainTask mainTask;
    private SenderTask senderTask;
    private ListenerTask listenerTask;

    private final Consumer<Integer> exitCallback;
    private final Consumer<Pair<AnsiColorFormat, String>> sendCallback;

    private boolean exiting;
    private boolean isDone;

    public CliClient(Consumer<Integer> exitCallback, Consumer<Pair<AnsiColorFormat, String>> sendCallback) {
        this.exitCallback = exitCallback;
        this.sendCallback = sendCallback;
        this.isDone = false;

        try {
            socket = new Socket("127.0.0.1", Constants.CLI__PORT);
        } catch (ConnectException e) {
            fatalError("ERROR: UERANSIM agent is not running.");
            return;
        } catch (Exception e) {
            fatalError("ERROR: CLI socket could not initialized.");
        }

        try {
            inputStream = socket.getInputStream();
        } catch (Exception e) {
            Utils.runUnchecked(() -> socket.close());

            fatalError("ERROR: Input socket could not open.");
            return;
        }

        try {
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            Utils.runUnchecked(() -> socket.close());
            Utils.runUnchecked(() -> inputStream.close());

            fatalError("ERROR: Input socket could not open.");
            return;
        }

        mainTask = new CliClient.MainTask();
        senderTask = new CliClient.SenderTask();
        listenerTask = new CliClient.ListenerTask();

        mainTask.start();
        senderTask.start();
        listenerTask.start();
    }

    public void start(String[] args) {
        CliOpt.msg = null;
        new CommandLine(new CliOpt.RootCommand())
                .registerConverter(OctetString.class, OctetString::new)
                .registerConverter(Supi.class, value -> {
                    var matcher = Pattern.compile("^(imsi-|ue-)?(\\d{15,16})$").matcher(value);
                    if (matcher.find())
                        return new Supi("imsi", matcher.group(2));
                    throw new IllegalArgumentException("Invalid IMSI format.");
                })
                .registerConverter(GnbId.class, value -> {
                    try {
                        return new GnbId(Integer.parseInt(value));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid GNB ID value.");
                    }
                })
                .execute(args);

        if (CliOpt.msg != null) {
            send(CliOpt.msg);
        } else {
            exit(1);
        }
    }

    protected void onReceive(CmdMessage cmd) {
        if (cmd instanceof CmdErrorIndication) {
            fatalError("ERROR: " + ((CmdErrorIndication) cmd).message);
        } else if (cmd instanceof CmdEcho) {
            sendCallback.accept(new Pair<>(null, ((CmdEcho) cmd).message));
        } else if (cmd instanceof CmdPrint) {
            sendCallback.accept(new Pair<>(null, ((CmdPrint) cmd).message));
        } else if (cmd instanceof CmdTerminate) {
            if (((CmdTerminate) cmd).finalOutput != null)
                sendCallback.accept(new Pair<>(null, ((CmdTerminate) cmd).finalOutput));

            exit(((CmdTerminate) cmd).code);
        }
    }

    protected final void send(CmdMessage cmd) {
        mainTask.push(cmd);
    }

     protected void fatalError(String message) {
        if (exiting) {
            finalExit(0);
            return;
        }

        sendCallback.accept(new Pair<>(AnsiPalette.PAINT_LOG_ERROR, message));
        finalExit(1);
    }

    protected void exit(int code) {
        exiting = true;

        Utils.runUnchecked(() -> inputStream.close());
        Utils.runUnchecked(() -> outputStream.close());
        Utils.runUnchecked(() -> socket.close());

        finalExit(code);
    }

    private void finalExit(int code) {
        if (isDone)
            return;

        isDone = true;
        exitCallback.accept(code);
        mainTask.releaseResources();
        senderTask.releaseResources();
        listenerTask.releaseResources();
    }

    public static class VersionProvider implements CommandLine.IVersionProvider {

        public String[] getVersion() {
            return new String[]{Constants.VERSION};
        }
    }

    private class MainTask extends NtsTask {

        public MainTask() {
            super(true);
        }

        @Override
        protected void main() {
            final int heartbeat = Constants.CLI__HEARTBEAT_PERIOD / 2;

            pushDelayed(new IwPerformCycle(Constants.CLI__CYCLE_TYPE_HEARTBEAT), heartbeat);

            while (true) {
                var msg = take();
                if (msg instanceof byte[]) {
                    var cmd = CliUtils.decodeCmdPdu((byte[]) msg, CliClient.this::fatalError);
                    onReceive(cmd);
                } else if (msg == SOCKET_CLOSE) {
                    if (!exiting) {
                        fatalError("ERROR: CLI socket closed by agent.");
                    }
                } else if (msg instanceof CmdMessage) {
                    senderTask.push(CliUtils.encodeCmdPdu((CmdMessage) msg));
                } else if (msg instanceof IwPerformCycle) {
                    if (((IwPerformCycle) msg).type == Constants.CLI__CYCLE_TYPE_HEARTBEAT) {
                        send(new CmdHeartBeat());
                        pushDelayed(msg, heartbeat);
                    }
                }
            }
        }
    }

    private class SenderTask extends NtsTask {

        @Override
        protected void main() {
            while (true) {
                var msg = take();
                if (msg instanceof byte[]) {
                    var data = (byte[]) msg;
                    try {
                        outputStream.write(data);
                        outputStream.flush();
                    } catch (Exception e) {
                        fatalError("ERROR: Command socket could not write.");
                    }
                }
            }
        }
    }

    private class ListenerTask extends NtsTask {

        @Override
        protected void main() {
            CliUtils.listenerLoop(inputStream, mainTask::push, () -> mainTask.push(SOCKET_CLOSE));
        }
    }
}
