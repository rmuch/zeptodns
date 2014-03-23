package zeptodns.protocol.messages;

import java.net.InetAddress;

/**
 * Represents an IPv4 "A" record.
 */
public class ARecord extends ResourceRecord {

    private InetAddress inetAddress;

    public ARecord() {
    }

    public ARecord(String name, InetAddress inetAddress) {
        this.setName(name);
        this.inetAddress = inetAddress;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    @Override
    public int getType() {
        return ResourceRecord.A;
    }

    @Override
    public void setType(int type) {
        throw new IllegalArgumentException("setType may not be called on type ARecord.");
    }

    @Override
    public int getClassCode() {
        return 1;
    }

    @Override
    public void setClassCode(int classCode) {
        throw new IllegalArgumentException("setClassCode may not be called on type ARecord.");
    }

    @Override
    public int getResourceDataLength() {
        return 4;
    }

    @Override
    public void setResourceDataLength(int resourceDataLength) {
        throw new IllegalArgumentException("setResourceDataLength may not be called on type ARecord.");
    }

    @Override
    public byte[] getResourceData() {
        return inetAddress.getAddress();
    }

    @Override
    public void setResourceData(byte[] resourceData) {
        throw new IllegalArgumentException("setResourceData may not be called on type ARecord.");
    }
}