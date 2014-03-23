package zeptodns.protocol.wire;

import zeptodns.protocol.messages.HeaderSection;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.QuestionSection;
import zeptodns.protocol.messages.ResourceRecord;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * Contains methods for parsing DNS protocol messages from NIO ByteBuffers.
 */
public class BinaryFormatter {
    /**
     * Reads a string from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return
     */
    public static String getSpecialString(ByteBuffer buffer) {
        StringBuilder stringBuilder = new StringBuilder();

        byte strLen;
        while ((strLen = buffer.get()) != 0) {
            byte[] strBytes = new byte[strLen];
            buffer.get(strBytes);

            stringBuilder.append(new String(strBytes));
            stringBuilder.append('.');
        }

        return stringBuilder.toString();
    }

    /**
     * Reads a message from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return
     */
    public static Message getMessage(ByteBuffer buffer) {
        Message message = new Message();

        HeaderSection header = getHeader(buffer);
        message.setHeaderSection(header);

        List<QuestionSection> questions = message.getQuestions();
        List<ResourceRecord> answers = message.getAnswers();
        List<ResourceRecord> authority = message.getAuthority();
        List<ResourceRecord> additional = message.getAdditional();

        for (int i = 0; i < header.getQuestionCount(); i++) {
            QuestionSection qs = getQuestionSection(buffer);
            questions.add(qs);
        }

        for (int i = 0; i < header.getAnswerCount(); i++) {
            ResourceRecord rr = getResourceRecord(buffer);
            answers.add(rr);
        }

        for (int i = 0; i < header.getNameServerCount(); i++) {
            ResourceRecord rr = getResourceRecord(buffer);
            authority.add(rr);
        }

        for (int i = 0; i < header.getAdditionalCount(); i++) {
            ResourceRecord rr = getResourceRecord(buffer);
            additional.add(rr);
        }

        return message;
    }

    /**
     * Reads a header section from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return
     */
    public static HeaderSection getHeader(ByteBuffer buffer) {
        buffer.order(ByteOrder.BIG_ENDIAN);

        int id;
        int flags;
        int questionCount;
        int answerCount;
        int nameServerCount;
        int additionalCount;

        id = buffer.getShort();
        flags = buffer.getShort();
        questionCount = buffer.getShort();
        answerCount = buffer.getShort();
        nameServerCount = buffer.getShort();
        additionalCount = buffer.getShort();

        return new HeaderSection(id, flags, questionCount, answerCount, nameServerCount, additionalCount);
    }

    /**
     * Reads a question section from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return
     */
    public static QuestionSection getQuestionSection(ByteBuffer buffer) {
        String name = getSpecialString(buffer);

        int qtype = (int) buffer.getShort();
        int qclass = (int) buffer.getShort();

        return new QuestionSection(name, qtype, qclass);
    }

    /**
     * Reads a resource record from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return
     */
    public static ResourceRecord getResourceRecord(ByteBuffer buffer) {
        String name = getSpecialString(buffer);

        int type = (int) buffer.getShort();
        int clasz = (int) buffer.getShort();
        long ttl = (long) buffer.getInt();
        int rdlength = (int) buffer.getShort();

        byte[] rdata = new byte[rdlength];
        buffer.get(rdata);

        ResourceRecord resourceRecord = new ResourceRecord();
        resourceRecord.setName(name);
        resourceRecord.setType(type);
        resourceRecord.setClassCode(clasz);
        resourceRecord.setTimeToLive(ttl);
        resourceRecord.setResourceDataLength(rdlength);
        resourceRecord.setResourceData(rdata);

        return resourceRecord;
    }

    /**
     * @param buffer the buffer to write to
     * @param string string to write
     */
    public static void putSpecialString(ByteBuffer buffer, String string) {
        String[] parts = string.split("\\.");

        for (String part : parts) {
            buffer.put((byte) part.length());
            buffer.put(part.getBytes());
        }

        buffer.put((byte) 0x00);
    }

    /**
     * @param buffer  the buffer to write to
     * @param message message to write
     */
    public static void putMessage(ByteBuffer buffer, Message message) {
        putHeader(buffer, message.getHeaderSection());

        for (QuestionSection questionSection : message.getQuestions()) {
            putQuestionSection(buffer, questionSection);
        }

        for (ResourceRecord resourceRecord : message.getAnswers()) {
            putResourceRecord(buffer, resourceRecord);
        }

        for (ResourceRecord resourceRecord : message.getAuthority()) {
            putResourceRecord(buffer, resourceRecord);
        }

        for (ResourceRecord resourceRecord : message.getAdditional()) {
            putResourceRecord(buffer, resourceRecord);
        }
    }

    /**
     * @param buffer the buffer to write to
     * @param header header to write
     */
    public static void putHeader(ByteBuffer buffer, HeaderSection header) {
        // Header sections are 12 bytes long, see RFC 1035 S 4.1.1.
        buffer.putShort((short) header.getId());
        buffer.putShort((short) header.getFlags());
        buffer.putShort((short) header.getQuestionCount());
        buffer.putShort((short) header.getAnswerCount());
        buffer.putShort((short) header.getNameServerCount());
        buffer.putShort((short) header.getAdditionalCount());
    }

    /**
     * @param buffer the buffer to write to
     * @param qs     question section to write
     */
    public static void putQuestionSection(ByteBuffer buffer, QuestionSection qs) {
        // TODO
    }

    /**
     * @param buffer the buffer to write to
     * @param res    resource record to write
     */
    public static void putResourceRecord(ByteBuffer buffer, ResourceRecord res) {
        putSpecialString(buffer, res.getName());

        buffer.putShort((short) res.getType());
        buffer.putShort((short) res.getClassCode());
        buffer.putInt((int) res.getTimeToLive());
        buffer.putShort((short) res.getResourceDataLength());
        buffer.put(res.getResourceData());
    }
}
