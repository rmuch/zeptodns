package zeptodns.protocol.messages;

/**
 * DNS response.
 */
public class Response {
    Message message;

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
