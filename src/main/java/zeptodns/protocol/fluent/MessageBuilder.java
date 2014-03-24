package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.ARecord;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.QuestionSection;
import zeptodns.protocol.wire.FlagUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Provides a fluent API for constructing DNS response packets.
 */
public class MessageBuilder implements
        MessageTypeStep,
        QueryParameterStep,
        ResponseParameterStep {

    private final Message message;

    /**
     * Initializes a new MessageBuilder with a blank message.
     */
    private MessageBuilder() {
        message = new Message();
    }

    /**
     * Begins building a DNS protocol message.
     *
     * @return message builder
     */
    public static MessageTypeStep begin() {
        return new MessageBuilder();
    }

    public QueryParameterStep asQuery() {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setQueryResponse(flags, 0);
        message.getHeaderSection().setFlags(flags);

        // set a random id
        Random random = new Random();
        int id = random.nextInt() & 0xFFFF;

        message.getHeaderSection().setId(id);

        return this;
    }

    public ResponseParameterStep asResponse() {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setQueryResponse(flags, 1);
        message.getHeaderSection().setFlags(flags);

        return this;
    }

    public ResponseParameterStep asResponse(Message queryMessage) {
        asResponse();

        message.getHeaderSection().setId(queryMessage.getHeaderSection().getId());

        return this;
    }

    public ResponseParameterStep asResponse(int id) {
        asResponse();

        message.getHeaderSection().setId(id);

        return this;
    }

    public ResponseParameterStep authoritative(boolean isAuthoritative) {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setAuthoritative(flags, isAuthoritative ? 1 : 0);
        message.getHeaderSection().setFlags(flags);

        return this;
    }

    public ResponseParameterStep withResponseCode(int responseCode) {
        int flags = message.getHeaderSection().getFlags();
        flags = FlagUtils.setResponseCode(flags, responseCode);
        message.getHeaderSection().setFlags(flags);

        return this;
    }

    public QueryParameterStep withQuestion(String name, int type, int clasz) {
        message.getHeaderSection().setQuestionCount(message.getHeaderSection().getQuestionCount() + 1);

        QuestionSection questionSection = new QuestionSection(name, type, clasz);
        message.getQuestions().add(questionSection);

        return this;
    }

    public ResponseParameterStep withARecord(String name, String addr) {
        message.getHeaderSection().setAnswerCount(message.getHeaderSection().getAnswerCount() + 1);

        try {
            ARecord aRecord = new ARecord(name, (Inet4Address) Inet4Address.getByName(addr));

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
