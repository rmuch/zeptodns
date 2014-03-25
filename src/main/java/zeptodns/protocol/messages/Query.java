package zeptodns.protocol.messages;

import java.net.SocketAddress;

/**
 * DNS query.
 */
public class Query {
    private Message message;
    private SocketAddress source;

    public Query(Message requestMessage) {
        message = requestMessage;
    }

    public Query(Message message, SocketAddress source) {
        this.message = message;
        this.source = source;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public SocketAddress getSource() {
        return source;
    }

    public void setSource(SocketAddress source) {
        this.source = source;
    }
}
