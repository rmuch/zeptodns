package zeptodns;

import zeptodns.handlers.NashornHandler;
import zeptodns.handlers.QueryHandler;
import zeptodns.nio.NioServer;

import javax.script.ScriptException;
import java.io.IOException;
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

        // Check arguments
        if (args.length < 1) {
            LOGGER.severe("No console arguments provided. Required: java -jar zeptodns.jar [script-path].");

            return;
        }

        // Attempt to set up query handler
        QueryHandler queryHandler;
        try {
            queryHandler = new NashornHandler(args[0]);
        } catch (IOException e) {
            LOGGER.severe("Could not read script provided as argument:\n" + e.getMessage());

            return;
        } catch (ScriptException e) {
            LOGGER.severe("Error precompiling script:\n" + e.getMessage());

            return;
        }

        ZeptoDNS zeptoDns = new ZeptoDNS(server, queryHandler);
        zeptoDns.run();
    }
}
