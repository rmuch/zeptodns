package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.Message;

public interface QueryParameterStep {
    /**
     * Adds a question section to the query under construction.
     *
     * @param name  the domain name being queried
     * @param type  the type of the query
     * @param clasz the class of the query
     * @return the next step in the fluent API
     */
    QueryParameterStep withQuestion(String name, int type, int clasz);

    /**
     * Finishes construction of the message.
     *
     * @return a newly constructed message
     */
    Message end();
}
