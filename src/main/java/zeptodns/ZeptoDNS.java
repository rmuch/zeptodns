package zeptodns;

import zeptodns.handlers.QueryHandler;
import zeptodns.nio.NioServer;
import zeptodns.protocol.fluent.MessageBuilder;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ZeptoDNS server main class.
 */
public class ZeptoDNS implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ZeptoDNS.class.getName());

    private NioServer server;
    private QueryHandler queryHandler;

    public ZeptoDNS() {
        server = new NioServer();

        queryHandler = query -> {

            Message message = MessageBuilder
                    .begin()
                    .asResponse()
                    .authoritative(true)
                    .withARecord(query.getMessage().getQuestions().get(0).getQuestionName(), "192.168.13.37")
                    .end();

            // TODO: Move this to the builder
            message.getHeaderSection().setId(query.getMessage().getHeaderSection().getId());

            return new Response(message);
        };

        server.setQueryHandler(queryHandler);
    }

    /**
     * Program entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ZeptoDNS zeptoDns = new ZeptoDNS();
        zeptoDns.run();
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Starting ZeptoDNS server connection loop...");

        server.run();
    }
}
