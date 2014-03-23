package zeptodns;

import zeptodns.handlers.QueryHandler;
import zeptodns.nio.NioServer;
import zeptodns.protocol.fluent.MessageBuilder;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.Response;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ZeptoDNS server main class.
 */
public class ZeptoDNS implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ZeptoDNS.class.getName());

    private final Server server;
    private final QueryHandler queryHandler;

    public ZeptoDNS(Server server, QueryHandler queryHandler) {
        this.server = server;
        this.queryHandler = queryHandler;

        server.setQueryHandler(queryHandler);
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Starting ZeptoDNS server...");

        server.run();
    }

    /**
     * Program entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        NioServer server = new NioServer(InetAddress.getLoopbackAddress(), 53);

        // TODO: this is only for testing
        QueryHandler queryHandler = query -> {
            Message message = MessageBuilder
                    .begin()
                    .asResponse(query.getMessage())
                    .authoritative(true)
                    .withARecord(query.getMessage().getQuestions().get(0).getQuestionName(), "192.168.13.37")
                    .withARecord(query.getMessage().getQuestions().get(0).getQuestionName(), "192.168.13.38")
                    .end();

            return new Response(message);
        };

        ZeptoDNS zeptoDns = new ZeptoDNS(server, queryHandler);
        zeptoDns.run();
    }
}
