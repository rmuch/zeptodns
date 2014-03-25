package zeptodns.protocol.messages;

/**
 * DNS response.
 */
public class Response {
    private Message message;

    public Response(Message responseMessage) {
        message = responseMessage;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
