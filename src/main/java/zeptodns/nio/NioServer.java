package zeptodns.nio;

import zeptodns.Server;
import zeptodns.handlers.QueryHandler;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.Query;
import zeptodns.protocol.messages.Response;
import zeptodns.protocol.wire.BinaryFormatter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * NIO connection handler.
 */
public class NioServer implements Server {
    private static final Logger LOGGER = Logger.getLogger(NioServer.class.getName());

    private final InetAddress bindAddress;
    private final int bindPort;

    private QueryHandler queryHandler;

    /**
     * Returns a new NIO server instance listening on the default port.
     */
    public NioServer() {
        this(InetAddress.getLoopbackAddress(), 53);
    }

    /**
     * Returns a new NIO server instance to listen on the specified port.
     *
     * @param port port to listen on
     */
    public NioServer(int port) {
        this(InetAddress.getLoopbackAddress(), port);
    }

    /**
     * Returns a new NIO server instance to listen on the specified address and port.
     *
     * @param address address to listen on
     * @param port    port to listen on
     */
    public NioServer(InetAddress address, int port) {
        bindAddress = address;
        bindPort = port;
    }

    @Override
    public QueryHandler getQueryHandler() {
        return queryHandler;
    }

    @Override
    public void setQueryHandler(QueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    /**
     * Performs read operations on a NIO selection key.
     *
     * @param key selection key
     * @throws IOException if an I/O error occurs
     */
    private void read(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        QueryStateAttachment queryStateAttachment = (QueryStateAttachment) key.attachment();

        // clear the request buffer
        queryStateAttachment.getRequestBuffer().clear();

        // receive data and set address
        queryStateAttachment.setRequestAddress(channel.receive(queryStateAttachment.getRequestBuffer()));

        // read the message
        queryStateAttachment.getRequestBuffer().flip();
        Message requestMessage = BinaryFormatter.getMessage(queryStateAttachment.getRequestBuffer());

        if (queryHandler != null) {
            Response response = queryHandler.handle(new Query(requestMessage));

            queryStateAttachment.getResponseBuffer().clear();
            BinaryFormatter.putMessage(queryStateAttachment.getResponseBuffer(), response.getMessage());
        } else {
            LOGGER.severe("Query handler is null.");
        }

        LOGGER.info("Handling query from " + queryStateAttachment.getRequestAddress().toString());
    }

    /**
     * Performs write operations on a NIO selection key.
     *
     * @param key selection key
     * @throws IOException if an I/O error occurs
     */
    private void write(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();

        QueryStateAttachment queryStateAttachment = (QueryStateAttachment) key.attachment();

        // Prepare response buffer.
        ByteBuffer responseBuffer = queryStateAttachment.getResponseBuffer();
        // We need to flip the buffer after writing to it.
        responseBuffer.flip();

        // Write data to the network.
        channel.send(responseBuffer, queryStateAttachment.getRequestAddress());

        LOGGER.info("Writing to " + queryStateAttachment.getRequestAddress());
    }

    /**
     * Begins listening on the interface specified in the class constructor.
     */
    @Override
    public void run() {
        try {
            // set up selector and datagram channel
            Selector selector = Selector.open();
            DatagramChannel datagramChannel = DatagramChannel.open();

            // bind non-blocking
            InetSocketAddress inetSocketAddress = new InetSocketAddress(bindAddress, bindPort);
            datagramChannel.socket().bind(inetSocketAddress);
            datagramChannel.configureBlocking(false);

            // Register channel for selection
            SelectionKey clientKey = datagramChannel.register(selector, SelectionKey.OP_READ);
            clientKey.attach(new QueryStateAttachment());

            while (true) {
                try {
                    selector.select();
                    Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

                    while (selectedKeys.hasNext()) {
                        try {
                            SelectionKey key = selectedKeys.next();
                            selectedKeys.remove();

                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isReadable()) {
                                read(key);

                                key.interestOps(SelectionKey.OP_WRITE);
                            } else if (key.isWritable()) {
                                write(key);

                                key.interestOps(SelectionKey.OP_READ);
                            }
                        } catch (IOException e) {
                            // Handle problem read/writing.
                            LOGGER.log(Level.WARNING, "Error I/O:\n" + e.toString());
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    // Handle problem selecting.
                    LOGGER.log(Level.SEVERE, "Error selecting:\n" + e.toString());
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            // Handle problem setting up bind.
            LOGGER.log(Level.SEVERE, "Error binding to interface:\n" + e.toString());
            e.printStackTrace();
        }
    }
}
