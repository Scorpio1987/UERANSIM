package tr.havelsan.ueransim.app.common.sw;

public class SwCommandResponse extends SocketWrapper {
    public final int transactionId;
    public final boolean isEnd;
    public final String color;
    public final String message;

    public SwCommandResponse(int transactionId, boolean isEnd, String color, String message) {
        this.transactionId = transactionId;
        this.isEnd = isEnd;
        this.color = color;
        this.message = message;
    }
}
