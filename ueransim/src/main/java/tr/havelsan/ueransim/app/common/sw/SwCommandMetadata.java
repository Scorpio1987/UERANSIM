package tr.havelsan.ueransim.app.common.sw;

import tr.havelsan.ueransim.app.app.entry.WebApp;

public class SwCommandMetadata extends SocketWrapper {
    public final WebApp.CommandMetadata commandMetadata;

    public SwCommandMetadata(WebApp.CommandMetadata commandMetadata) {
        this.commandMetadata = commandMetadata;
    }
}
