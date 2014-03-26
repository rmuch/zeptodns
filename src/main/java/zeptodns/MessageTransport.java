package zeptodns;

import zeptodns.handlers.QueryHandler;

/**
 * Accepts DNS queries,
 */
public interface MessageTransport extends Runnable {
    /**
     * Returns the handler for queries accepted by this server.
     * @return the QueryHandler assigned to this server
     */
    QueryHandler getQueryHandler();

    /**
     * Sets the handler for queries accepted by this server.
     * @param queryHandler the QueryHandler assigned to this server
     */
    void setQueryHandler(QueryHandler queryHandler);

    /**
     * Begins accepting connections and processing query responses using the specified QueryHandler.
     */
    void run();
}
