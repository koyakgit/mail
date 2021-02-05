package mail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class MailRec {
    public void receive() {
        try {
            // メール受信のプロパティ設定
            Properties props = new Properties();
            props.put("mail.pop3.host", "host.docker.internal");
            // props.put("mail.pop3.port", "110");

            // ----- for SSL
            props.put("mail.pop3s.host", "host.docker.internal");
            props.put("mail.pop3s.port", "995");
            props.put("mail.pop3s.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.pop3s.socketFactory.fallback", "false");
            props.put("mail.pop3s.socketFactory.port", "995");
            props.put("mail.pop3s.ssl.trust", "*");
            // -------------

            // メール受信フォルダをオープン
            Session session = Session.getDefaultInstance(props);
            try (Store store = session.getStore("pop3s")) {
                store.connect("fronteo01", "fronteo01");
                Folder folderInbox = store.getFolder("INBOX");
                folderInbox.open(Folder.READ_ONLY);
                System.out.println("opened." + String.valueOf(folderInbox.getMessages().length));
                // メッセージ一覧を取得
                Message[] arrayMessages = folderInbox.getMessages();
                for (int lc = 0; lc < arrayMessages.length; lc++) {
                    // メッセージの取得
                    Message message = arrayMessages[lc];
                    // 件名の取得と表示
                    String subject = message.getSubject();
                    System.out.print("件名：" + subject + "\r\n");
                    // 本文の取得と表示
                    String content = message.getContent().toString();
                    System.out.print("本文：" + content + "\r\n");
                    // 取得の最大件数は１０件
                    if (lc >= 10) {
                        break;
                    }
                    System.out.print("\r\n");
                }
            }
            System.out.println("successful !!");
        } catch (Exception e) {
            System.out.print("例外が発生！");
            e.printStackTrace();
        } finally {
        }
    }
}
