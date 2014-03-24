package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.Message;

public interface MessageTypeStep {
    /**
     * Continues construction of the message as a query.
     *
     * @return message builder
     */
    QueryParameterStep asQuery();

    /**
     * Continues construction of the message as a response.
     *
     * @return
     */
    ResponseParameterStep asResponse();

    /**
     * Continues construction of the message as a response to a provided query message.
     *
     * @param queryMessage
     * @return
     */
    ResponseParameterStep asResponse(Message queryMessage);

    /**
     * Continues construction of the message as a response with a provided ID.
     *
     * @param id
     * @return
     */
    ResponseParameterStep asResponse(int id);
}
