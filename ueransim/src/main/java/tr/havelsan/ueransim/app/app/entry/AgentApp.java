/*
 * Copyright (c) 2020 ALİ GÜNGÖR (aligng1620@gmail.com)
 * This software and all associated files are licensed under GPL-3.0.
 */

package tr.havelsan.ueransim.app.app.entry;

import tr.havelsan.ueransim.app.app.AppBuilder;
import tr.havelsan.ueransim.app.app.AppConfig;
import tr.havelsan.ueransim.app.app.UeRanSim;
import tr.havelsan.ueransim.app.app.cli.CliTask;
import tr.havelsan.ueransim.app.app.cli.DispatchMonitor;
import tr.havelsan.ueransim.app.app.monitor.LoadTestMonitor;
import tr.havelsan.ueransim.app.app.monitor.MonitorTask;
import tr.havelsan.ueransim.app.app.monitor.TimelineMonitor;
import tr.havelsan.ueransim.app.common.enums.EConnType;
import tr.havelsan.ueransim.app.common.simctx.BaseSimContext;
import tr.havelsan.ueransim.app.common.simctx.GnbSimContext;
import tr.havelsan.ueransim.app.common.simctx.UeSimContext;
import tr.havelsan.ueransim.app.common.sw.SwIntervalResult;
import tr.havelsan.ueransim.app.common.sw.SwLog;
import tr.havelsan.ueransim.nts.nts.NtsTask;
import tr.havelsan.ueransim.utils.Tag;
import tr.havelsan.ueransim.utils.Utils;
import tr.havelsan.ueransim.utils.console.LogEntry;
import tr.havelsan.ueransim.utils.console.Logger;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AgentApp {

    private UeRanSim ueransim;
    private AppConfig appConfig;

    private CliTask cliTask;
    private DispatchMonitor dispatchMonitor;
    private LogTask logTask;
    private Consumer<LogEntry> logHandler;

    private WebInterface webInterface;

    public static void main(String[] args) {
        BaseApp.main(args, true);
        new AgentApp().main();
    }

    private void main() {
        appConfig = new AppConfig();

        dispatchMonitor = new DispatchMonitor(); // started by ueransim instance

        webInterface = new WebInterface();

        logTask = new LogTask(webInterface::onMessage);
        logTask.start();

        logHandler = logEntry -> {
            if (logEntry != null && logEntry.tag != Tag.MSG)
                logTask.push(logEntry);
        };
        Logger.GLOBAL.addLogHandler(logHandler);

        ueransim = new AppBuilder()
                .addMonitor(dispatchMonitor)
                .addMonitor(new NodeInitializerTask(logHandler))
                .addMonitor(new TimelineMonitor(webInterface::onMessage))
                .addMonitor(new LoadTestNotifierMonitor(webInterface::onMessage))
                .build();

        cliTask = new CliTask(appConfig, ueransim);
        cliTask.start();

        webInterface.start();
    }

    private static class LoadTestNotifierMonitor extends LoadTestMonitor {
        private final Consumer<Object> intervalConsumer;

        public LoadTestNotifierMonitor(Consumer<Object> intervalConsumer) {
            this.intervalConsumer = intervalConsumer;
        }

        @Override
        protected void onIntervalResult(String id, String display, boolean isSuccess, String nodeName, long deltaMs) {
           intervalConsumer.accept(new SwIntervalResult(id, isSuccess, nodeName, deltaMs));
        }
    }

    private static class LogTask extends NtsTask {
        private final Consumer<SwLog> logConsumer;

        public LogTask(Consumer<SwLog> logConsumer) {
            this.logConsumer = logConsumer;
        }

        @Override
        protected void main() {
            // In this task, we use buffering instead of sending the logs one by one.
            while (true) {
                Utils.sleep(150);

                var list = new ArrayList<LogEntry>();

                while (true) {
                    var entry = (LogEntry) poll();
                    if (entry != null)
                        list.add(entry);
                    else break;
                }

                if (!list.isEmpty())
                    logConsumer.accept(new SwLog(list));
            }
        }
    }

    private static class NodeInitializerTask extends MonitorTask {
        private final Consumer<LogEntry> logHandler;

        public NodeInitializerTask(Consumer<LogEntry> logHandler) {
            this.logHandler = logHandler;
        }

        @Override
        protected void onCreate(BaseSimContext ctx) {
            if (ctx instanceof UeSimContext) {
                ((UeSimContext) ctx).logger.addLogHandler(logHandler);
            } else if (ctx instanceof GnbSimContext) {
                ((GnbSimContext) ctx).logger.addLogHandler(logHandler);
            }
        }

        @Override
        protected void onConnected(BaseSimContext ctx, EConnType connType) {

        }

        @Override
        protected void onSend(BaseSimContext ctx, Object message) {

        }

        @Override
        protected void onReceive(BaseSimContext ctx, Object message) {

        }

        @Override
        protected void onSwitched(BaseSimContext ctx, Enum<?> state) {

        }
    }
}
