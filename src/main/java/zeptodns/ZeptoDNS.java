package zeptodns;

import org.apache.commons.cli.*;
import zeptodns.handlers.NashornHandler;
import zeptodns.handlers.QueryHandler;
import zeptodns.nio.NioUdpTransport;

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

    private final MessageTransport messageTransport;
    private final QueryHandler queryHandler;

    public ZeptoDNS(MessageTransport messageTransport, QueryHandler queryHandler) {
        this.messageTransport = messageTransport;
        this.queryHandler = queryHandler;

        messageTransport.setQueryHandler(queryHandler);
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Starting ZeptoDNS server...");

        messageTransport.run();
    }

    /**
     * Program entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int port = 53; // Listen on 53 by default.
        String scriptPath;

        // set up options
        Options options = new Options();
        options.addOption("p", "port", true, "UDP port to listen on");
        options.addOption("s", "script", true, "path to script file");

        // parse
        CommandLineParser parser = new GnuParser();

        try {
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("p")) {
                port = Integer.parseInt(commandLine.getOptionValue("p"));
            }

            if (commandLine.hasOption("s")) {
                scriptPath = commandLine.getOptionValue("s");
            } else {
                System.err.println("error: script file not provided");

                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("zeptodns", options);

                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();

            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("zeptodns", options);

            return;
        }

        NioUdpTransport server = new NioUdpTransport(InetAddress.getLoopbackAddress(), port);

        // Attempt to set up query handler
        QueryHandler queryHandler;
        try {
            queryHandler = new NashornHandler(scriptPath);
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
