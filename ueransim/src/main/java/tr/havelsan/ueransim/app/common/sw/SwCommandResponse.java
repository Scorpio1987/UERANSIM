package tr.havelsan.ueransim.app.common.sw;

import tr.havelsan.ueransim.utils.jcolor.AnsiColorFormat;

public class SwCommandResponse extends SocketWrapper {
    public final int transactionId;
    public final boolean isEnd;
    public final AnsiColorFormat color;
    public final String message;

    public SwCommandResponse(int transactionId, boolean isEnd, AnsiColorFormat color, String message) {
        this.transactionId = transactionId;
        this.isEnd = isEnd;
        this.color = color;
        this.message = message;
    }
}
