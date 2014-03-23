package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.ARecord;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.wire.FlagUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Provides a fluent API for constructing DNS response packets.
 */
public class MessageBuilder implements
        QueryResponseStep,
        QueryMessageParameterStep,
        ResponseMessageParameterStep {

    private final Message message;

    private MessageBuilder() {
        message = new Message();
    }

    public static QueryResponseStep begin() {
        return new MessageBuilder();
    }

    public QueryMessageParameterStep asQuery() {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setQueryResponse(flags, 0);
        message.getHeaderSection().setFlags(flags);

        // TODO: Set random ID

        return this;
    }

    public ResponseMessageParameterStep asResponse() {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setQueryResponse(flags, 1);
        message.getHeaderSection().setFlags(flags);

        return this;
    }

    public ResponseMessageParameterStep asResponse(Message queryMessage) {
        asResponse();

        message.getHeaderSection().setId(queryMessage.getHeaderSection().getId());

        return this;
    }

    public ResponseMessageParameterStep asResponse(int id) {
        asResponse();

        message.getHeaderSection().setId(id);

        return this;
    }

    public ResponseMessageParameterStep authoritative(boolean isAuthoritative) {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setAuthoritative(flags, isAuthoritative ? 1 : 0);
        message.getHeaderSection().setFlags(flags);

        return this;
    }

    /**
     * Sets the response code value of the message under construction.
     *
     * @param responseCode response code value
     * @return next step in the fluent API
     * @see FlagUtils for RCODE values
     */
    public ResponseMessageParameterStep withResponseCode(int responseCode) {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setResponseCode(flags, responseCode);
        message.getHeaderSection().setFlags(flags);

        return this;
    }

    public QueryMessageParameterStep withQuestion() {
        message.getHeaderSection().setQuestionCount(message.getHeaderSection().getQuestionCount() + 1);

        // TODO

        return this;
    }

    public ResponseMessageParameterStep withARecord(String name, String addr) {
        message.getHeaderSection().setAnswerCount(message.getHeaderSection().getAnswerCount() + 1);

        try {
            ARecord aRecord = new ARecord(name, InetAddress.getByName(addr));

            message.getAnswers().add(aRecord);
        } catch (UnknownHostException e) {
            // TODO: Pass this on?
            e.printStackTrace();
        }

        return this;
    }

    public Message end() {
        return message;
    }

}
