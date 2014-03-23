package zeptodns.protocol.messages;

import java.net.Inet6Address;

public class AAAARecord extends ResourceRecord {
    private Inet6Address inetAddress;

    public AAAARecord() {
    }

    public AAAARecord(String name, Inet6Address inetAddress) {
        this.setName(name);
        this.inetAddress = inetAddress;
    }

    public Inet6Address getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(Inet6Address inetAddress) {
        this.inetAddress = inetAddress;
    }

    @Override
    public int getType() {
        return ResourceRecord.A;
    }

    @Override
    public void setType(int type) {
        throw new IllegalArgumentException("setType may not be called on type AAAARecord.");
    }

    @Override
    public int getClassCode() {
        return 1;
    }

    @Override
    public void setClassCode(int classCode) {
        throw new IllegalArgumentException("setClassCode may not be called on type AAAARecord.");
    }

    @Override
    public int getResourceDataLength() {
        return 16;
    }

    @Override
    public void setResourceDataLength(int resourceDataLength) {
        throw new IllegalArgumentException("setResourceDataLength may not be called on type AAAARecord.");
    }

    @Override
    public byte[] getResourceData() {
        return inetAddress.getAddress();
    }

    @Override
    public void setResourceData(byte[] resourceData) {
        throw new IllegalArgumentException("setResourceData may not be called on type AAAARecord.");
    }
}
