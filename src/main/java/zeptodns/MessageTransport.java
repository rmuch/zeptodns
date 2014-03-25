package zeptodns;

import zeptodns.handlers.QueryHandler;

/**
 * Accepts DNS queries,
 */
public interface MessageTransport extends Runnable {
    /**
     *
     * @return
     */
    QueryHandler getQueryHandler();

    /**
     *
     * @param queryHandler
     */
    void setQueryHandler(QueryHandler queryHandler);

    /**
     * Begins accepting connections and processing query responses using the specified QueryHandler.
     */
    void run();
}
