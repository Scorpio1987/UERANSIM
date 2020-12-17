/*
 * Copyright (c) 2020 ALİ GÜNGÖR (aligng1620@gmail.com)
 * This software and all associated files are licensed under GPL-3.0.
 */

package tr.havelsan.ueransim.app.app.entry;

import io.javalin.Javalin;
import io.javalin.websocket.WsContext;
import picocli.CommandLine;
import tr.havelsan.ueransim.app.app.AppBuilder;
import tr.havelsan.ueransim.app.app.AppConfig;
import tr.havelsan.ueransim.app.app.UeRanSim;
import tr.havelsan.ueransim.app.app.cli.CliOpt;
import tr.havelsan.ueransim.app.app.monitor.LoadTestMonitor;
import tr.havelsan.ueransim.app.app.monitor.StepperMonitor;
import tr.havelsan.ueransim.app.common.Supi;
import tr.havelsan.ueransim.app.common.sw.*;
import tr.havelsan.ueransim.app.utils.SocketWrapperSerializer;
import tr.havelsan.ueransim.itms.nts.NtsTask;
import tr.havelsan.ueransim.utils.Fun;
import tr.havelsan.ueransim.utils.Severity;
import tr.havelsan.ueransim.utils.Tag;
import tr.havelsan.ueransim.utils.Utils;
import tr.havelsan.ueransim.utils.console.Log;
import tr.havelsan.ueransim.utils.console.LogEntry;
import tr.havelsan.ueransim.utils.exceptions.NotImplementedException;
import tr.havelsan.ueransim.utils.octets.OctetString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

// TODO: What if multiple WS clients connect us?
public class WebApp {

    private ReceiverTask receiverTask;
    private SenderTask senderTask;
    private LogTask logTask;

    private UeRanSim ueransim;

    public WebApp() {
        Fun initTasks = () -> {
            receiverTask = new ReceiverTask();
            senderTask = new SenderTask();
            logTask = new LogTask();

            receiverTask.senderTask = senderTask;
            senderTask.receiverTask = receiverTask;
            logTask.senderTask = senderTask;

            senderTask.start();
            receiverTask.start();
            logTask.start();
        };

        Fun initLogger = () -> {
            Consumer<LogEntry> logHandler = logTask::push;

            Log.addLogHandler(logHandler);

            for (var ueId : ueransim.allUes()) {
                var ue = ueransim.findUe(ueId);
                if (ue != null) ue.logger.addLogHandler(logHandler);
            }

            for (var gnbId : ueransim.allGnbs()) {
                var gnb = ueransim.findGnb(gnbId);
                if (gnb != null) gnb.logger.addLogHandler(logHandler);
            }
        };

        Fun initJavalin = () -> Javalin.create().start(1071).ws("/web-interface", ws -> {
            ws.onConnect(wsc -> senderTask.push(new OnConnected(wsc)));
            ws.onMessage(wsc -> receiverTask.push(SocketWrapperSerializer.fromJson(wsc.message(), SocketWrapper.class)));
        });

        Fun buildApp = () -> {
            var stepperMonitor = new StepperMonitor(senderTask::push);
            var loadTestNotifierMonitor = new LoadTestNotifierMonitor(senderTask);
            var appConfig = new AppConfig();

            ueransim = new AppBuilder()
                    .addMonitor(stepperMonitor)
                    .addMonitor(loadTestNotifierMonitor)
                    .build();
        };

        initTasks.run();
        buildApp.run();
        initJavalin.run();
    }

    public static void main(String[] args) {
        BaseApp.main(args, true);
        new WebApp();
    }

    private static class OnConnected {
        private final WsContext ws;

        public OnConnected(WsContext ws) {
            this.ws = ws;
        }
    }

    private static class ReceiverTask extends NtsTask {
        public SenderTask senderTask;

        @Override
        protected void main() {
            while (true) {
                var msg = take();
                if (msg instanceof SwCommand) {
                    // TODO
                }
            }
        }
    }

    private static class SenderTask extends NtsTask {
        public ReceiverTask receiverTask;
        private WsContext ws;

        @Override
        protected void main() {
            while (true) {
                var msg = take();
                if (msg instanceof OnConnected) {
                    ws = ((OnConnected) msg).ws;
                    push(new SwIntervalMetadata(LoadTestMonitor.IntervalMetadata.INSTANCE));
                    push(new SwLogMetadata(Severity.values(), Tag.values()));
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

    private static class LogTask extends NtsTask {
        public SenderTask senderTask;

        @Override
        protected void main() {
            // In this task, we use buffering instead of sending the logs one by one.
            while (true) {
                Utils.sleep(150);

                if (senderTask == null)
                    continue;

                var list = new ArrayList<LogEntry>();

                while (true) {
                    var entry = (LogEntry) poll();
                    if (entry != null)
                        list.add(entry);
                    else break;
                }

                if (!list.isEmpty()) {
                    senderTask.push(new SwLog(list));
                }
            }
        }
    }

    private static class LoadTestNotifierMonitor extends LoadTestMonitor {
        public final SenderTask senderTask;

        public LoadTestNotifierMonitor(SenderTask senderTask) {
            this.senderTask = senderTask;
        }

        @Override
        protected void onIntervalResult(String id, String display, boolean isSuccess, String nodeName, long deltaMs) {
            senderTask.push(new SwIntervalResult(id, isSuccess, nodeName, deltaMs));
        }
    }

    public static class CommandMetadata {
        public static CommandMetadata INSTANCE = new CommandMetadata();

        public final List<CommandInfo> commands;

        private CommandMetadata() {
            commands = new ArrayList<>();

            var rootCommand = new CommandLine(new CliOpt.RootCommand());
            for (var subCommand : rootCommand.getSubcommands().values()) {
                if (!subCommand.getSubcommands().isEmpty())
                    continue;

                var name = subCommand.getCommandName();
                var spec = subCommand.getCommandSpec();
                var command = (CommandLine.Command) subCommand.getCommand().getClass().getAnnotation(CommandLine.Command.class);
                var description = String.join(" ", command.description());
                var parameters = new ArrayList<ParameterInfo>();

                for (var param : spec.positionalParameters()) {
                    parameters.add(getParameterInfo(param));
                }

                for (var option : spec.options()) {
                    if (option.usageHelp() || option.versionHelp())
                        continue;
                    parameters.add(getParameterInfo(option));
                }

                // TODO: help
                commands.add(new CommandInfo(name, description, parameters));
            }
        }

        private ParameterInfo getParameterInfo(CommandLine.Model.ArgSpec arg) {
            var name = arg.paramLabel();
            var description = String.join(" ", arg.description());
            var type = getParamType(arg.type());
            var isPositional = arg.isPositional();
            var isRequired = arg.required();
            return new ParameterInfo(name, description, type, isPositional, isRequired);
        }

        private ParameterType getParamType(Class<?> cls) {
            if (cls == boolean.class)
                return ParameterType.BOOL;
            if (cls == int.class || cls == long.class)
                return ParameterType.INTEGER;
            if (cls == float.class || cls == double.class)
                return ParameterType.FLOAT;
            if (cls == String.class)
                return ParameterType.TEXT;
            if (cls == OctetString.class)
                return ParameterType.OCTET_STRING;
            if (cls == File.class)
                return ParameterType.FILE;
            if (cls == Supi.class)
                throw new NotImplementedException(""); // TODO
            throw new IllegalArgumentException();
        }
    }

    private static class CommandInfo {
        public final String name;
        public final String description;
        public final List<ParameterInfo> parameters;

        public CommandInfo(String name, String description, List<ParameterInfo> parameters) {
            this.name = name;
            this.description = description;
            this.parameters = parameters;
        }
    }

    private static class ParameterInfo {
        public final String name;
        public final String description;
        public final ParameterType type;
        public final boolean isPositional;
        public final boolean isRequired;

        public ParameterInfo(String name, String description, ParameterType type, boolean isPositional, boolean isRequired) {
            this.name = name;
            this.description = description;
            this.type = type;
            this.isPositional = isPositional;
            this.isRequired = isRequired;
        }
    }

    private enum ParameterType {
        BOOL,
        INTEGER,
        FLOAT,
        TEXT,
        IMSI,
        OCTET_STRING,
        FILE,
    }
}
