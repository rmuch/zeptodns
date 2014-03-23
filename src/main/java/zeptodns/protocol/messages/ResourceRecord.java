package zeptodns.protocol.messages;

/**
 * DNS resource record.
 */
public class ResourceRecord {
    /**
     * Type code for an address record.
     */
    public static final int A = 1;

    /**
     * Type code for an IPv6 address record.
     */
    public static final int AAAA = 28;

    private String name; // len prefix (1) + string + null
    private int type;
    private int classCode;
    private long timeToLive;
    private int resourceDataLength;
    private byte[] resourceData;

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

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getResourceDataLength() {
        return resourceDataLength;
    }

    public void setResourceDataLength(int resourceDataLength) {
        this.resourceDataLength = resourceDataLength;
    }

    public byte[] getResourceData() {
        return resourceData;
    }

    public void setResourceData(byte[] resourceData) {
        this.resourceData = resourceData;
    }
}
