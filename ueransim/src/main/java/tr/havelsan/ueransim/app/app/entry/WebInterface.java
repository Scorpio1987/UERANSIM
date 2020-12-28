package tr.havelsan.ueransim.app.app.entry;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;
import tr.havelsan.ueransim.app.app.cli.CliClient;
import tr.havelsan.ueransim.app.app.cli.CliUtils;
import tr.havelsan.ueransim.app.common.sw.SocketWrapper;
import tr.havelsan.ueransim.app.common.sw.SwCommand;
import tr.havelsan.ueransim.app.common.sw.SwCommandResponse;
import tr.havelsan.ueransim.app.utils.SocketWrapperSerializer;
import tr.havelsan.ueransim.nts.nts.NtsTask;
import tr.havelsan.ueransim.utils.Utils;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: What if multiple WS clients connect us?
public class WebInterface {
    private ReceiverTask receiverTask;
    private SenderTask senderTask;

    public void start() {
        receiverTask = new ReceiverTask();
        senderTask = new SenderTask();

        receiverTask.senderTask = senderTask;
        senderTask.receiverTask = receiverTask;

        senderTask.start();
        receiverTask.start();

        var server = new WebSocketServer(new InetSocketAddress("localhost", 1071)) {
            @Override
            public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
                ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
                if (!request.getResourceDescriptor().equals("/web-interface")) {
                    throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, "Invalid endpoint");
                }
                return builder;
            }

            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
                senderTask.push(new OnConnected(webSocket));
            }

            @Override
            public void onMessage(WebSocket webSocket, String s) {
                receiverTask.push(SocketWrapperSerializer.fromJson(s, SocketWrapper.class));
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onClose(WebSocket webSocket, int i, String s, boolean b) {
            }

            @Override
            public void onError(WebSocket webSocket, Exception e) {
            }
        };

        server.setReuseAddr(true); // TODO
        server.start();
    }

    public void onMessage(Object message) {
        senderTask.push(message);
    }

    private static class OnConnected {
        public final WebSocket ws;

        public OnConnected(WebSocket ws) {
            this.ws = ws;
        }
    }

    private static class ReceiverTask extends NtsTask {
        public SenderTask senderTask;
        private static final AtomicInteger ID_PROVIDER = new AtomicInteger();

        @Override
        protected void main() {
            while (true) {
                var msg = take();
                if (msg instanceof SwCommand) {
                    int transactionId = ID_PROVIDER.getAndIncrement();
                    var args = CliUtils.constructCommandLineArgs((SwCommand) msg);
                    new CliClient(code -> {
                        senderTask.push(new SwCommandResponse(transactionId, true, null, null));
                    }, colorMessage -> {
                        senderTask.push(new SwCommandResponse(transactionId, false, colorMessage.first, colorMessage.second));
                    }).start(args);
                }
            }
        }
    }

    private static class SenderTask extends NtsTask {
        public ReceiverTask receiverTask;
        private WebSocket ws;

        @Override
        protected void main() {
            while (true) {
                var msg = take();
                if (msg instanceof OnConnected) {
                    ws = ((OnConnected) msg).ws;
                    push(Metadata.COMMAND_METADATA);
                    push(Metadata.CONFIG_METADATA);
                    push(Metadata.INTERVAL_METADATA);
                    push(Metadata.LOG_METADATA);
                } else if (msg instanceof SocketWrapper) {
                    if (ws != null) {
                        ws.send(SocketWrapperSerializer.toJson(msg));
                    } else {
                        push(msg);
                        Utils.sleep(10);
                    }
                }
            }
        }
    }
}
