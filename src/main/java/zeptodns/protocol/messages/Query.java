package zeptodns.protocol.messages;

/**
 * DNS query.
 */
public class Query {
    private Message message;

    public Query(Message requestMessage) {
        message = requestMessage;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
