package zeptodns;

import zeptodns.handlers.QueryHandler;

public interface Server {
    QueryHandler getQueryHandler();

    void setQueryHandler(QueryHandler queryHandler);

    void run();
}
