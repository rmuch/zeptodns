package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.Message;

public interface ResponseParameterStep {
    /**
     * Sets the authoritative flag of the response.
     *
     * @param isAuthoritative true if authoritative, false otherwise
     * @return message builder
     */
    ResponseParameterStep authoritative(boolean isAuthoritative);

    /**
     * Sets the response code value of the message under construction.
     *
     * @param responseCode response code value
     * @return next step in the fluent API
     * @see zeptodns.protocol.wire.FlagUtils for RCODE values
     */
    ResponseParameterStep withResponseCode(int responseCode);

    /**
     * Adds an A record to the query under construction.
     *
     * @param name record name
     * @param addr string representation of IPv4 address
     * @return message builder
     */
    ResponseParameterStep withARecord(String name, String addr);

    /**
     * Finishes construction of the message.
     *
     * @return a newly constructed message
     */
    Message end();
}
