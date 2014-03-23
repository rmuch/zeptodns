package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.Message;

public interface ResponseMessageParameterStep {
    ResponseMessageParameterStep authoritative(boolean isAuthoritative);

    public ResponseMessageParameterStep withARecord(String name, String addr);

    Message end();
}
