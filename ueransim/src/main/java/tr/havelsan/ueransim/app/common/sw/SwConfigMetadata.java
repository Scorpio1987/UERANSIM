package tr.havelsan.ueransim.app.common.sw;

import tr.havelsan.ueransim.app.app.entry.Metadata;

public class SwConfigMetadata extends SocketWrapper {
    public final Metadata.ConfigMetadata configMetadata;

    public SwConfigMetadata(Metadata.ConfigMetadata configMetadata) {
        this.configMetadata = configMetadata;
    }
}
