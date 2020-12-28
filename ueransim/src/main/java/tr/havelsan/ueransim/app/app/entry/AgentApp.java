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
import tr.havelsan.ueransim.app.app.monitor.StepperMonitor;
import tr.havelsan.ueransim.app.common.sw.SwIntervalResult;

import java.util.function.Consumer;

public class AgentApp {

    private UeRanSim ueransim;
    private AppConfig appConfig;

    private CliTask cliTask;
    private DispatchMonitor dispatchMonitor;

    private WebInterface webInterface;

    public static void main(String[] args) {
        BaseApp.main(args, true);
        new AgentApp().main();
    }

    private void main() {
        appConfig = new AppConfig();

        dispatchMonitor = new DispatchMonitor(); // started by ueransim instance

        webInterface = new WebInterface();

        ueransim = new AppBuilder()
                .addMonitor(dispatchMonitor)
                .addMonitor(new StepperMonitor(webInterface::onMessage))
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
}
