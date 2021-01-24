# メール受信環境セットアップ手順

## メールサーバの構築、起動

1. Docker for Windowsをインストール

2. 任意の場所にmailserver-docker.zipを解凍

3. エクスプローラーから解凍フォルダの下のDockerフォルダを開く

4. Shift＋右クリックで「PowerShellウインドウをここで開く」と表示されるのでクリック

5. 以下のコマンドを実行

   ```sh
   docker build -t mailserver .
   ```

   - 成功すれば最後に以下のように表示される

     ```sh
     Complete!
     Removing intermediate container aeb16f58f171
      ---> f1572dda6fe0
     Successfully built f1572dda6fe0
     Successfully tagged mailserver:latest
     SECURITY WARNING: You are building a Docker image from Windows against a non-Windows Docker host. All files and directories added to build context will have '-rwxr-xr-x' permissions. It is recommended to double check and reset permissions for sensitive files and directories.
     ```

6. コンテナをたちあげる

   ```sh
   docker-compose up -d
   ```

7. 起動確認

   ```sh
   docker ps
   ```

   ```sh
   CONTAINER ID   IMAGE        COMMAND        CREATED         STATUS         PORTS                                                                                    NAMES
   7bf46e2b85ae   mailserver   "/sbin/init"   8 seconds ago   Up 7 seconds   0.0.0.0:110->110/tcp, 0.0.0.0:143->143/tcp, 0.0.0.0:993->993/tcp, 0.0.0.0:995->995/tcp   mailserver
   ```

8. たちあげたコンテナ（メールサーバ）にログイン

   ```sh
   docker exec -it mailserver /bin/bash
   ```

   

9. メール送受信ユーザ(fronteo01)のパスワード設定

   ```sh
   passwd fronteo01
   ```

   ```sh
   Changing password for user fronteo01.
   New password:
   Retype new password:
   passwd: all authentication tokens updated successfully.
   ```

10. ポート番号が正常に開放Listen状態になっていることを確認

    ```sh
    ss -tlsn
    ```

    ```sh
    Total: 151
    TCP:   19 (estab 0, closed 12, orphaned 0, timewait 0)
    
    Transport Total     IP        IPv6
    RAW       0         0         0
    UDP       1         1         0
    TCP       7         4         3
    INET      8         5         3
    FRAG      0         0         0
    
    State      Recv-Q     Send-Q         Local Address:Port           Peer Address:Port
    LISTEN     0          128               127.0.0.11:35905               0.0.0.0:*
    LISTEN     0          100                  0.0.0.0:110                 0.0.0.0:*
    LISTEN     0          100                  0.0.0.0:143                 0.0.0.0:*
    LISTEN     0          100                  0.0.0.0:25                  0.0.0.0:*
    LISTEN     0          100                     [::]:110                    [::]:*
    LISTEN     0          100                     [::]:143                    [::]:*
    LISTEN     0          100                     [::]:25                     [::]:*
    ```

11. 受信サーバが正常に起動していることを確認

    ```sh
    systemctl status dovecot
    ```

    ```sh
    ● dovecot.service - Dovecot IMAP/POP3 email server
       Loaded: loaded (/usr/lib/systemd/system/dovecot.service; enabled; vendor preset: dis>
       Active: active (running) since Sun 2021-01-24 23:04:02 JST; 4min 8s ago
         Docs: man:dovecot(1)
               http://wiki2.dovecot.org/
      Process: 128 ExecStartPre=/usr/libexec/dovecot/prestartscript (code=exited, status=0/>
     Main PID: 131 (dovecot)
        Tasks: 4 (limit: 163602)
       Memory: 5.5M
       CGroup: /docker/028edfcc8b55be8b0fb7a14f39c19cc560956026171f4286cdbfa361955e7531/sys>
               ├─131 /usr/sbin/dovecot -F
               ├─136 dovecot/anvil
               ├─137 dovecot/log
               └─138 dovecot/config
    ```

12. 送信サーバが正常に起動していることを確認

    ```sh
    systemctl status postfix
    ```

    ```sh
    ● postfix.service - Postfix Mail Transport Agent
       Loaded: loaded (/usr/lib/systemd/system/postfix.service; enabled; vendor preset: dis>
       Active: active (running) since Sun 2021-01-24 23:04:03 JST; 5min ago
      Process: 96 ExecStart=/usr/sbin/postfix start (code=exited, status=0/SUCCESS)
      Process: 88 ExecStartPre=/usr/libexec/postfix/chroot-update (code=exited, status=0/SU>
      Process: 43 ExecStartPre=/usr/libexec/postfix/aliasesdb (code=exited, status=0/SUCCES>
     Main PID: 173 (master)
        Tasks: 3 (limit: 163602)
       Memory: 7.3M
       CGroup: /docker/028edfcc8b55be8b0fb7a14f39c19cc560956026171f4286cdbfa361955e7531/sys>
               ├─173 /usr/libexec/postfix/master -w
               ├─174 pickup -l -t unix -u
               └─175 qmgr -l -t unix -u
    ```

13. telnetからメールを送信（メールアドレスは"fronteo01@fronteo.local"）

    ```sh
    telnet localhost 25
    ```

    ```
    Trying 127.0.0.1...
    Connected to localhost.
    Escape character is '^]'.
    220 mail.fronteo.local ESMTP Postfix
    ```

    ```sh
    helo localhost
    ```

    ```
    250 mail.fronteo.local
    ```

    ```
    mail from: fronteo01@fronteo.local
    ```

    ```
    250 2.1.0 Ok
    ```

    ```
    rcpt to: fronteo01@fronteo.local
    ```

    ```
    250 2.1.5 Ok
    ```

    ```
    data
    ```

    ```
    354 End data with <CR><LF>.<CR><LF>
    ```

    ```
    Subject: test mail
    Hello world !!
    .
    ```

    ```
    250 2.0.0 Ok: queued as D72F519E2
    ```

    ```
    quit
    ```

    ```
    221 2.0.0 Bye
    Connection closed by foreign host.
    ```

14. メールサーバのログを確認

    ```sh
    less /var/log/maillog
    (Shift + F)
    ```

    ```
    (途中略)
    Jan 24 14:16:08 mailserver postfix/qmgr[175]: D72F519E2: removed
    Jan 24 14:16:59 mailserver postfix/smtpd[208]: disconnect from localhost[127.0.0.1] helo=1 mail=1 rcpt=1 data=1 quit=1 commands=5
    (Ctrl+C -> q)
    ```

    （エラーの類は出力されていないことを確認）

15. メールが受信サーバから参照できることを確認

    ```sh
    mutt
    ```

    ```
    q:Quit  d:Del  u:Undel  s:Save  m:Mail  r:Reply  g:Group  ?:Help
    1      1月 24 fronteo01@front (0.1K) test mail
    ```

    (選択すると本文も参照できる)

## クライアントの設定

| 設定項目                       | 設定値                                                       |
| ------------------------------ | ------------------------------------------------------------ |
| 受信サーバに接続するユーザ名   | fronteo01                                                    |
| 受信サーバに接続するパスワード | (上記手順で入力したパスワード)                               |
| メールアドレス                 | fronteo01@fronteo.local                                      |
| ホスト                         | host.docker.internal<br>別サーバから接続する場合は、host.docker.internalに紐づくIPアドレス<br>(ping host.docker.internalとpower shellでうてば確認可) |
| ポート                         | 受信サーバ：プロトコルに対応したポート番号<br>送信サーバ：25 |
| 証明書                         | POP3sまたはIMAPsを使う場合に必要                             |

## POP3s, IMAPsでの確認

1. 以下の手順を参照してサーバ、クライアント証明書を作成

   1. [コチラ](https://server-setting.info/centos/private-ca-cert.html#install)の「２．プライベート認証局のCA証明書を作成します」から実施
   2. アドレスの類は「fronteo.bil.com」に置き換え

2. 以下の手順を参照してdovecotの設定を変更

   ```sh
   # vi /etc/dovecot/conf.d/10-ssl.conf
   ```

   ```
   ssl = yes
   ssl_cert = /etc/pki/CA/certs/fronteo.bil.com.pem
   ssl_key = /etc/pki/CA/private/fronteo.bil.com.key
   ```

3. 発行した証明書をサーバ、クライアントそれぞれに設定

4. dovecot再起動

   ```sh
   systemctl restart dovecot
   ```

5. クライアント側も再起動（再実行）

