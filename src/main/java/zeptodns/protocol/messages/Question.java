package zeptodns.protocol.messages;

/**
 * RFC 1035 question section.
 */
public class Question {
    private String name;
    private int type;
    private int clasz;

    public Question() {
    }

    public Question(String name, int type, int clasz) {
        this.name = name;
        this.type = type;
        this.clasz = clasz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClasz() {
        return clasz;
    }

    public void setClasz(int clasz) {
        this.clasz = clasz;
    }
}
