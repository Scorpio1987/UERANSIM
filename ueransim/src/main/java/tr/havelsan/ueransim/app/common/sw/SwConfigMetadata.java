package tr.havelsan.ueransim.app.common.sw;

import tr.havelsan.ueransim.app.app.entry.WebApp;

public class SwConfigMetadata extends SocketWrapper {
    public final WebApp.ConfigMetadata configMetadata;

    public SwConfigMetadata(WebApp.ConfigMetadata configMetadata) {
        this.configMetadata = configMetadata;
    }
}
