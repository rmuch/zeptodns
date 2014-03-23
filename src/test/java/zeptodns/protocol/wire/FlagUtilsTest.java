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
}
