package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.Message;

public interface QueryParameterStep {
    /**
     * @param name
     * @param type
     * @param clasz
     * @return
     */
    QueryParameterStep withQuestion(String name, int type, int clasz);

    /**
     * @return
     */
    Message end();
}
