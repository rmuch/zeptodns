package zeptodns.protocol.messages;

import java.util.LinkedList;
import java.util.List;

/**
 * DNS message, as defined in RFC 1035 (4. MESSAGES).
 */
public class Message {
    private Header header;
    private List<Question> questions;
    private List<ResourceRecord> answers;
    private List<ResourceRecord> authority;
    private List<ResourceRecord> additional;

    public Message() {
        header = new Header();
        questions = new LinkedList<>();
        answers = new LinkedList<>();
        authority = new LinkedList<>();
        additional = new LinkedList<>();
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
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
