package zeptodns.protocol.messages;

import java.util.LinkedList;
import java.util.List;

/**
 * DNS message, as defined by RFC 1035 S. 4.1
 */
public class Message {
    private HeaderSection headerSection;
    private List<QuestionSection> questions;
    private List<ResourceRecord> answers;
    private List<ResourceRecord> authority;
    private List<ResourceRecord> additional;

    public Message() {
        headerSection = new HeaderSection();
        questions = new LinkedList<>();
        answers = new LinkedList<>();
        authority = new LinkedList<>();
        additional = new LinkedList<>();
    }

    public HeaderSection getHeaderSection() {
        return headerSection;
    }

    public void setHeaderSection(HeaderSection headerSection) {
        this.headerSection = headerSection;
    }

    public List<QuestionSection> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionSection> questions) {
        this.questions = questions;
    }

    public List<ResourceRecord> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ResourceRecord> answers) {
        this.answers = answers;
    }

    public List<ResourceRecord> getAuthority() {
        return authority;
    }

    public void setAuthority(List<ResourceRecord> authority) {
        this.authority = authority;
    }

    public List<ResourceRecord> getAdditional() {
        return additional;
    }

    public void setAdditional(List<ResourceRecord> additional) {
        this.additional = additional;
    }
}
