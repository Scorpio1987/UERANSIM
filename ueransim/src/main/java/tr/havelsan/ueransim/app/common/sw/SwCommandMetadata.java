package tr.havelsan.ueransim.app.common.sw;

import tr.havelsan.ueransim.app.app.entry.Metadata;

public class SwCommandMetadata extends SocketWrapper {
    public final Metadata.CommandMetadata commandMetadata;

    public SwCommandMetadata(Metadata.CommandMetadata commandMetadata) {
        this.commandMetadata = commandMetadata;
    }
}
