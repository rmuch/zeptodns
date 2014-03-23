package zeptodns.protocol.fluent;

public interface QueryResponseStep {
    QueryMessageParameterStep asQuery();

    ResponseMessageParameterStep asResponse();
}
