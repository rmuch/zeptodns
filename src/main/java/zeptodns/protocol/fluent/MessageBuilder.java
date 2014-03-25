package zeptodns.protocol.fluent;

import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.Question;
import zeptodns.protocol.messages.records.ARecord;
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
        int flags = message.getHeader().getFlags();
        flags = FlagUtils.setQueryResponse(flags, 0);
        message.getHeader().setFlags(flags);

        // set a random id
        Random random = new Random();
        int id = random.nextInt() & 0xFFFF;

        message.getHeader().setId(id);

        return this;
    }

    public ResponseParameterStep asResponse() {
        int flags = message.getHeader().getFlags();
        flags = FlagUtils.setQueryResponse(flags, 1);
        message.getHeader().setFlags(flags);

        return this;
    }

    public ResponseParameterStep asResponse(Message queryMessage) {
        asResponse();

        message.getHeader().setId(queryMessage.getHeader().getId());

        return this;
    }

    public ResponseParameterStep asResponse(int id) {
        asResponse();

        message.getHeader().setId(id);

        return this;
    }

    public ResponseParameterStep authoritative(boolean isAuthoritative) {
        int flags = message.getHeader().getFlags();
        flags = FlagUtils.setAuthoritative(flags, isAuthoritative ? 1 : 0);
        message.getHeader().setFlags(flags);

        return this;
    }

    public ResponseParameterStep withResponseCode(int responseCode) {
        int flags = message.getHeader().getFlags();
        flags = FlagUtils.setResponseCode(flags, responseCode);
        message.getHeader().setFlags(flags);

        return this;
    }

    public QueryParameterStep withQuestion(String name, int type, int clasz) {
        message.getHeader().setQuestionCount(message.getHeader().getQuestionCount() + 1);

        Question question = new Question(name, type, clasz);
        message.getQuestions().add(question);

        return this;
    }

    public ResponseParameterStep withARecord(String name, String addr) {
        message.getHeader().setAnswerCount(message.getHeader().getAnswerCount() + 1);

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
