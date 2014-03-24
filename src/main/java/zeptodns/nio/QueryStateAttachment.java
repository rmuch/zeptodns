package zeptodns.nio;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

/**
 * Contains server state data.
 */
public class QueryStateAttachment {
    // RFC 1035 S 4.2.1 restricts UDP packet size to 512 bytes
    public static final int PACKET_SIZE = 512;

    private ByteBuffer requestBuffer;
    private ByteBuffer responseBuffer;
    private SocketAddress requestAddress;

    public QueryStateAttachment() {
        this(ByteBuffer.allocate(PACKET_SIZE), ByteBuffer.allocate(PACKET_SIZE), null);
    }

    public QueryStateAttachment(ByteBuffer requestBuffer, ByteBuffer responseBuffer, SocketAddress requestAddress) {
        this.requestBuffer = requestBuffer;
        this.responseBuffer = responseBuffer;
        this.requestAddress = requestAddress;
    }

    public ByteBuffer getRequestBuffer() {
        return requestBuffer;
    }

    public void setRequestBuffer(ByteBuffer requestBuffer) {
        this.requestBuffer = requestBuffer;
    }

    public ByteBuffer getResponseBuffer() {
        return responseBuffer;
    }

    public void setResponseBuffer(ByteBuffer responseBuffer) {
        this.responseBuffer = responseBuffer;
    }

    public SocketAddress getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(SocketAddress requestAddress) {
        this.requestAddress = requestAddress;
    }
}
