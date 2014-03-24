package zeptodns.protocol.fluent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.wire.FlagUtils;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MessageBuilderTest {
    @Test
    public void thatMessageBuilderQueryIsValid() {
        Message message = MessageBuilder
                .begin()
                .asQuery()
                .end();

        assertEquals(FlagUtils.getQueryResponse(message.getHeaderSection().getFlags()), 0);
    }

    @Test
    public void thatMessageBuilderResponseIsValid() {
        Message message = MessageBuilder
                .begin()
                .asResponse()
                .end();

        assertEquals(FlagUtils.getQueryResponse(message.getHeaderSection().getFlags()), 1);
    }
}
