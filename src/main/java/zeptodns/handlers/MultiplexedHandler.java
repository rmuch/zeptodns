package zeptodns.handlers;

import zeptodns.protocol.messages.Query;
import zeptodns.protocol.messages.Response;

import java.util.List;

/**
 * Multiplexed query handler.
 */
public class MultiplexedHandler implements QueryHandler {
    private final List<QueryHandler> queryHandlers;

    public MultiplexedHandler(List<QueryHandler> queryHandlers) {
        this.queryHandlers = queryHandlers;
    }

    @Override
    public Response handle(Query query) {
        Response response = null;

        for (QueryHandler queryHandler : queryHandlers) {
            response = queryHandler.handle(query);

            if (response != null)
                break;
        }

        return response;
    }
}
