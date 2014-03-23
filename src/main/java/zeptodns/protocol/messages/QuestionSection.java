package zeptodns.protocol.messages;

/**
 * RFC 1035 question section.
 */
public class QuestionSection {
    private String questionName;
    private int questionType;
    private int questionClass;

    public QuestionSection() {
    }

    public QuestionSection(String questionName, int questionType, int questionClass) {
        this.questionName = questionName;
        this.questionType = questionType;
        this.questionClass = questionClass;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public int getQuestionClass() {
        return questionClass;
    }

    public void setQuestionClass(int questionClass) {
        this.questionClass = questionClass;
    }
}
