package zeptodns.protocol.wire;

import zeptodns.protocol.messages.Header;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.Question;
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
     * @return string from the buffer
     */
    public static String getOctetString(ByteBuffer buffer) {
        StringBuilder stringBuilder = new StringBuilder();

        // read octet containing the length until we get to null
        byte strLen;
        while ((strLen = buffer.get()) != 0x00) {
            byte[] strBytes = new byte[strLen];
            buffer.get(strBytes);

            // append that to the string builder followed by the separator character
            stringBuilder.append(new String(strBytes));
            stringBuilder.append('.');
        }

        return stringBuilder.toString();
    }

    /**
     * Reads a message from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return message from the buffer
     */
    public static Message getMessage(ByteBuffer buffer) {
        Message message = new Message();

        Header header = getHeader(buffer);
        message.setHeader(header);

        List<Question> questions = message.getQuestions();
        List<ResourceRecord> answers = message.getAnswers();
        List<ResourceRecord> authority = message.getAuthority();
        List<ResourceRecord> additional = message.getAdditional();

        for (int i = 0; i < header.getQuestionCount(); i++) {
            Question question = getQuestion(buffer);
            questions.add(question);
        }

        for (int i = 0; i < header.getAnswerCount(); i++) {
            ResourceRecord resource = getResourceRecord(buffer);
            answers.add(resource);
        }

        for (int i = 0; i < header.getNameServerCount(); i++) {
            ResourceRecord resource = getResourceRecord(buffer);
            authority.add(resource);
        }

        for (int i = 0; i < header.getAdditionalCount(); i++) {
            ResourceRecord resource = getResourceRecord(buffer);
            additional.add(resource);
        }

        return message;
    }

    /**
     * Reads a header section from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return header section from the buffer
     */
    public static Header getHeader(ByteBuffer buffer) {
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

        return new Header(id, flags, questionCount, answerCount, nameServerCount, additionalCount);
    }

    /**
     * Reads a question section from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return question section from the buffer
     */
    public static Question getQuestion(ByteBuffer buffer) {
        String name = getOctetString(buffer);

        int type = (int) buffer.getShort();
        int clasz = (int) buffer.getShort();

        return new Question(name, type, clasz);
    }

    /**
     * Reads a resource record from a ByteBuffer.
     *
     * @param buffer the buffer to read from
     * @return resource record from the buffer
     */
    public static ResourceRecord getResourceRecord(ByteBuffer buffer) {
        String name = getOctetString(buffer);

        int type = (int) buffer.getShort();
        int clasz = (int) buffer.getShort();
        long ttl = (long) buffer.getInt();
        int resourceDataLength = (int) buffer.getShort();

        byte[] resourceData = new byte[resourceDataLength];
        buffer.get(resourceData);

        ResourceRecord resourceRecord = new ResourceRecord();
        resourceRecord.setName(name);
        resourceRecord.setType(type);
        resourceRecord.setClassCode(clasz);
        resourceRecord.setTimeToLive(ttl);
        resourceRecord.setResourceDataLength(resourceDataLength);
        resourceRecord.setResourceData(resourceData);

        return resourceRecord;
    }

    /**
     * Writes a DNS string to a NIO buffer.
     *
     * @param buffer the buffer to write to
     * @param string string to write
     */
    public static void putOctetString(ByteBuffer buffer, String string) {
        String[] parts = string.split("\\.");

        // a single octet contains a length prefix, followed by that number of characters
        for (String part : parts) {
            buffer.put((byte) part.length());
            buffer.put(part.getBytes());
        }

        // the string is finally terminated by 0x00
        buffer.put((byte) 0x00);
    }

    /**
     * Writes a message to a NIO buffer.
     *
     * @param buffer  the buffer to write to
     * @param message message to write
     */
    public static void putMessage(ByteBuffer buffer, Message message) {
        putHeader(buffer, message.getHeader());

        for (Question question : message.getQuestions()) {
            putQuestion(buffer, question);
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
     * Writes a header to a NIO buffer.
     *
     * @param buffer the buffer to write to
     * @param header header to write
     */
    public static void putHeader(ByteBuffer buffer, Header header) {
        // Header sections are 12 bytes long, see RFC 1035 S 4.1.1.
        buffer.putShort((short) header.getId());
        buffer.putShort((short) header.getFlags());
        buffer.putShort((short) header.getQuestionCount());
        buffer.putShort((short) header.getAnswerCount());
        buffer.putShort((short) header.getNameServerCount());
        buffer.putShort((short) header.getAdditionalCount());
    }

    /**
     * Writes a question section to a NIO buffer.
     *
     * @param buffer the buffer to write to
     * @param question     question section to write
     */
    public static void putQuestion(ByteBuffer buffer, Question question) {
        putOctetString(buffer, question.getName());

        buffer.putShort((short) question.getType());
        buffer.putShort((short) question.getClasz());
    }

    /**
     * Writes a resource record to a NIO buffer.
     *
     * @param buffer the buffer to write to
     * @param resource    resource record to write
     */
    public static void putResourceRecord(ByteBuffer buffer, ResourceRecord resource) {
        putOctetString(buffer, resource.getName());

        buffer.putShort((short) resource.getType());
        buffer.putShort((short) resource.getClassCode());
        buffer.putInt((int) resource.getTimeToLive());
        buffer.putShort((short) resource.getResourceDataLength());
        buffer.put(resource.getResourceData());
    }
}
