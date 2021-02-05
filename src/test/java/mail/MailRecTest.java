package mail;

import org.junit.Test;

public class MailRecTest {
    @Test
    public void testExec() {
        final MailRec rec = new MailRec();
        rec.receive();
    }
}
