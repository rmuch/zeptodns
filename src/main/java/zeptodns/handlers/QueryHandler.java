package zeptodns.handlers;

import zeptodns.protocol.messages.Query;
import zeptodns.protocol.messages.Response;

/**
 * Provides an interface for taking a query and returning a response.
 */
@FunctionalInterface
public interface QueryHandler {
    Response handle(Query query);
}
