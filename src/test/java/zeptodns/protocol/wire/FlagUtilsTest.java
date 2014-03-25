package zeptodns.protocol.wire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FlagUtilsTest {
    @Test
    public void thatQueryResponseFlagIsSetCorrectly() {
        int flags = 0;

        flags = FlagUtils.setQueryResponse(flags, 1);

        assertEquals(flags, 1);

        flags = FlagUtils.setQueryResponse(flags, 0);

        assertEquals(flags, 0);
    }

    @Test
    public void thatFlagsSetAndOverlapCorrectly() {
        int flags = 0;

        flags = FlagUtils.setQueryResponse(flags, 1);
        flags = FlagUtils.setOpcode(flags, 2);
        flags = FlagUtils.setAuthoritative(flags, 1);
        flags = FlagUtils.setTruncation(flags, 0);
        flags = FlagUtils.setRecursionDesired(flags, 0);
        flags = FlagUtils.setRecursionAvailable(flags, 1);
        flags = FlagUtils.setReserved(flags, 2);
        flags = FlagUtils.setResponseCode(flags, 1);

        assertEquals(flags, 5413);

        assertEquals(FlagUtils.getQueryResponse(flags), 1);
        assertEquals(FlagUtils.getOpcode(flags), 2);
        assertEquals(FlagUtils.getAuthoritative(flags), 1);
        assertEquals(FlagUtils.getTruncation(flags), 0);
        assertEquals(FlagUtils.getRecursionDesired(flags), 0);
        assertEquals(FlagUtils.getRecursionAvailable(flags), 1);
        assertEquals(FlagUtils.getReserved(flags), 2);
        assertEquals(FlagUtils.getResponseCode(flags), 1);
    }
}
