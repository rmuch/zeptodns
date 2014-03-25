package zeptodns.protocol.messages;

/**
 * RFC 1035 header section.
 */
public class Header {
    private int id;
    private int flags;
    private int questionCount;
    private int answerCount;
    private int nameServerCount;
    private int additionalCount;

    public Header() {
    }

    public Header(int id, int flags, int questionCount, int answerCount, int nameServerCount, int additionalCount) {
        this.id = id;
        this.flags = flags;
        this.questionCount = questionCount;
        this.answerCount = answerCount;
        this.nameServerCount = nameServerCount;
        this.additionalCount = additionalCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getNameServerCount() {
        return nameServerCount;
    }

    public void setNameServerCount(int nameServerCount) {
        this.nameServerCount = nameServerCount;
    }

    public int getAdditionalCount() {
        return additionalCount;
    }

    public void setAdditionalCount(int additionalCount) {
        this.additionalCount = additionalCount;
    }
}
