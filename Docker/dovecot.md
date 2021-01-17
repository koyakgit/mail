# メールサーバ構築方法（dovecot）

## 参考

- [dovecot(pop3,imap)のインストール、設定](https://server-setting.info/centos/dovecot.html)

## 確認

- 受信ポート

  ```sh
  [root@mailserver dovecot]# ss -ltu
  Netid     State      Recv-Q     Send-Q          Local Address:Port            Peer Address:Port
  udp       UNCONN     0          0                  127.0.0.11:40438                0.0.0.0:*
  tcp       LISTEN     0          100                   0.0.0.0:imaps                0.0.0.0:*
  tcp       LISTEN     0          100                   0.0.0.0:pop3s                0.0.0.0:*
  tcp       LISTEN     0          128                127.0.0.11:36677                0.0.0.0:*
  tcp       LISTEN     0          100                   0.0.0.0:pop3                 0.0.0.0:*
  tcp       LISTEN     0          100                   0.0.0.0:imap                 0.0.0.0:*
  tcp       LISTEN     0          100                      [::]:imaps                   [::]:*
  tcp       LISTEN     0          100                      [::]:pop3s                   [::]:*
  tcp       LISTEN     0          100                      [::]:pop3                    [::]:*
  tcp       LISTEN     0          100                      [::]:imap                    [::]:*
  ```

  

## 手順

