package zeptodns.protocol.wire;

/**
 * Helper class for setting DNS header flags.
 */
public class FlagUtils {
    // QR flag
    public static final int QR_QUERY = 0;
    public static final int QR_RESPONSE = 1;

    // Opcode
    public static final int OPCODE_QUERY = 0;
    public static final int OPCODE_IQUERY = 1;
    public static final int OPCODE_STATUS = 2;

    // return code
    public static final int RCODE_NO_ERROR = 0;
    public static final int RCODE_FORMAT_ERROR = 1;
    public static final int RCODE_SERVER_FAILURE = 2;
    public static final int RCODE_NAME_ERROR = 3;
    public static final int RCODE_NOT_IMPLEMENTED = 4;
    public static final int RCODE_REFUSED = 5;

    // Flags
    /**
     * Specifies whether a message is a query (0), or a response (1).
     */
    public static final int FLAGS_QR = 0;
    public static final int FLAGS_OPCODE = 1;
    public static final int FLAGS_AA = 5;
    public static final int FLAGS_TC = 6;
    public static final int FLAGS_RD = 7;
    public static final int FLAGS_RA = 8;
    public static final int FLAGS_Z = 9;
    public static final int FLAGS_RCODE = 12;

    /**
     * Sets a specific flag value.
     *
     * @param flags
     * @param offset
     * @param mask
     * @param value
     * @return
     */
    public static int setFlagValue(int flags, int offset, int mask, int value) {
        // Mask value
        value &= mask;
        // shift value by offset
        value <<= offset;
        // zero out masked + offset area
        flags &= ~(mask << offset);
        // apply value to flags
        flags |= value;

        return flags;
    }

    /**
     * Gets a specific flag value.
     *
     * @param flags
     * @param offset
     * @param mask
     * @return
     */
    public static int getFlagValue(int flags, int offset, int mask) {
        // shift by offset
        flags >>= offset;
        // mask
        flags &= mask;

        return flags;
    }

    public static int setQueryResponse(int flags, int qr) {
        return setFlagValue(flags, FLAGS_QR, 0x01, qr);
    }

    public static int setOpcode(int flags, int opcode) {
        return setFlagValue(flags, FLAGS_OPCODE, 0x04, opcode);
    }

    public static int setAuthoritative(int flags, int aa) {
        return setFlagValue(flags, FLAGS_AA, 0x01, aa);
    }

    public static int setTruncation(int flags, int tc) {
        return setFlagValue(flags, FLAGS_TC, 0x01, tc);
    }

    public static int setRecursionDesired(int flags, int rd) {
        return setFlagValue(flags, FLAGS_RD, 0x01, rd);
    }

    public static int setRecursionAvailable(int flags, int ra) {
        return setFlagValue(flags, FLAGS_RA, 0x01, ra);
    }

    public static int setReserved(int flags, int z) {
        return setFlagValue(flags, FLAGS_Z, 0x03, z);
    }

    public static int setResponseCode(int flags, int rcode) {
        return setFlagValue(flags, FLAGS_RCODE, 0x04, rcode);
    }

    public static int getQueryResponse(int flags) {
        return getFlagValue(flags, FLAGS_QR, 0x01);
    }

    public static int getOpcode(int flags) {
        return getFlagValue(flags, FLAGS_OPCODE, 0x04);
    }

    public static int getAuthoritative(int flags) {
        return getFlagValue(flags, FLAGS_AA, 0x01);
    }

    public static int getTruncation(int flags) {
        return getFlagValue(flags, FLAGS_TC, 0x01);
    }

    public static int getRecursionDesired(int flags) {
        return getFlagValue(flags, FLAGS_RD, 0x01);
    }

    public static int getRecursionAvailable(int flags) {
        return getFlagValue(flags, FLAGS_RA, 0x01);
    }

    public static int getReserved(int flags) {
        return getFlagValue(flags, FLAGS_Z, 0x03);
    }

    public static int getResponseCode(int flags) {
        return getFlagValue(flags, FLAGS_RCODE, 0x04);
    }
}
