package zeptodns.protocol.fluent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import zeptodns.protocol.messages.Message;

@RunWith(JUnit4.class)
public class MessageBuilderTest {
    @Test
    public void thatMessageBuilderQueryIsValid() {
        Message message = MessageBuilder
                .begin()
                .asQuery()
                .end();

        // TODO
    }
}
