package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.Message;

public interface QueryResponseStep {
    QueryMessageParameterStep asQuery();

    ResponseMessageParameterStep asResponse();

    ResponseMessageParameterStep asResponse(Message queryMessage);

    ResponseMessageParameterStep asResponse(int id);
}
