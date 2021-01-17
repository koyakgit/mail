# メールサーバ構築方法（証明書発行）

## 参考

- [証明書発行手順](https://server-setting.info/centos/private-ca-cert.html#install)

## 操作手順

1. メールサーバ構築用Dockerイメージの作成

   ```docker build -t mailserver .```

2. 作成したイメージからコンテナを起動

   ```docker-compose up -d```

3. コンテナの中に入る

   ```docker exec -it mailserver /bin/bash```

4. 以下の手順で証明書発行

   ```sh
   # 新規にCA証明書を作成します。
   $ cd /etc/pki/tls/
   $ CADAYS="-days 3650" SSLEAY_CONFIG="-config /etc/pki/tls/openssl-ca.cnf" /etc/pki/tls/misc/CA -newca
   ...
   
   CA certificate filename (or enter to create) 
   
   Making CA certificate ...
   Generating a 2048 bit RSA private key
   .......................................+++
   ........+++
   writing new private key to '/etc/.../CA/private/./cakey.pem'
   # パスフレーズ（パスワード）を入力します。
   Enter PEM pass phrase: 
   # パスフレーズ（パスワード）を再入力します。
   Verifying - Enter PEM pass phrase: 
   -----
   You are about to be asked to enter information that will be incorporated
   into your certificate request.
   What you are about to enter is what is called a Distinguished Name or a DN.
   There are quite a few fields but you can leave some blank
   For some fields there will be a default value,
   If you enter '.', the field will be left blank.
   -----
   # デフォルト([]内表示)で良い場合は、そのままRETURNでOKです。
   # JP: 日本
   Country Name (2 letter code) [JP]: JP
   # Tokyo:東京都
   State or Province Name (full name) [Tokyo]: Tokyo
   # Setagaya:世田谷区
   Locality Name (eg, city) [Setagaya]: Setagaya
   # 会社名 or ドメイン名
   Organization Name (eg, company) [example.com]: example.com
   # 部署などであるが、ここではそのままリターン
   Organizational Unit Name (eg, section) []: 
   # CAの名前 : あなたの名前かサイト名
   Common Name (eg, your name or your server's hostname) []: Private CA
   # 管理者メールアドレス
   Email Address [webmaster@example.com]: webmaster@example.com
   
   Please enter the following 'extra' attributes
   to be sent with your certificate request
   # そのままリターン
   A challenge password []: 
   # そのままリターン
   An optional company name []: 
   Using configuration from /etc/.../openssl-ca.cnf
   # 先に入力したパスフレーズ（パスワード）を再入力
   Enter pass phrase for /etc/.../CA/private/./cakey.pem: 
   Check that the request matches the signature
   Signature ok
   Certificate Details:
           Serial Number:
               d6:38:3b:73:e8:1d:0c:0b
           Validity
               Not Before: Feb 16 09:08:41 2015 GMT
               Not After : Feb 13 09:08:41 2025 GMT
           Subject:
               countryName               = JP
               stateOrProvinceName       = Tokyo
               organizationName          = example.com
               commonName                = Private CA
               emailAddress              = webmaster@example.com
           X509v3 extensions:
               X509v3 Subject Key Identifier:
                   FF:5F:89:2E:9C:87:0C:62:3E:67:5A:8E:20:BE:5E:C2:2E:5F:6E:AF
               X509v3 Authority Key Identifier:
                   keyid:FF:5F:89:2E:9C:87:0C:62:3E:67:5A:8E:20:BE:5E:C2:2E:5F:6E:AF
   
               X509v3 Basic Constraints:
                   CA:TRUE
               Netscape Cert Type:
                   SSL CA, S/MIME CA
   Certificate is to be certified until Feb 13 09:08:41 2025 GMT (3650 days)
   
   Write out database with 1 new entries
   Data Base Updated
   
   # ここで作成した認証局用の各ファイルを確認しておきます。
   $ ls -1 /etc/pki/CA/*
   /etc/pki/CA/cacert.pem      # CA証明書(CAのcakey.pem で署名した公開鍵)
   /etc/pki/CA/careq.pem       # CAのCSR (CAの自己署名する前の公開鍵)
   /etc/pki/CA/index.txt       # CA管理ファイル（証明書管理情報）
   /etc/pki/CA/index.txt.attr  # CA管理ファイル（属性）
   /etc/pki/CA/index.txt.old   # CA管理ファイル（一つ前）
   /etc/pki/CA/serial
   
   /etc/pki/CA/certs:          # 証明書ディレクトリ
   
   /etc/pki/CA/newcerts:       # 新しい証明書ディレクトリ
   C0DBFD1232845AA7.pem        # 新規に作成した証明書が、随時、作成されていきます。(ファイル名は、作成した時、環境によって異なる)
                               # このファイルは、./CA/cacert.pem と同じです。管理用なので特に意識する必要はありません、
   
   /etc/pki/CA/private:        # 秘密鍵ディレクトリ
   cakey.pem                   # CA秘密鍵
   ```

5. サーバ証明書の作成

   ```sh
   # 新規にサーバー証明書を作成します。
   $ cd /etc/pki/tls/
   $ DAYS="-days 3650" SSLEAY_CONFIG="-config /etc/pki/tls/openssl-server.cnf" /etc/pki/tls/misc/CA -newreq
   ...
   Generating a 2048 bit RSA private key
   ......+++
   ..........+++
   writing new private key to 'newkey.pem'
   # サーバー用のパスフレーズ（パスワード）を入力します。
   Enter PEM pass phrase: 
   # サーバー用のパスフレーズ（パスワード）を再入力します。
   Verifying - Enter PEM pass phrase: 
   -----
   You are about to be asked to enter information that will be incorporated
   into your certificate request.
   What you are about to enter is what is called a Distinguished Name or a DN.
   There are quite a few fields but you can leave some blank
   For some fields there will be a default value,
   If you enter '.', the field will be left blank.
   -----
   # デフォルト([]内表示)で良い場合は、そのままRETURNでOKです。
   # JP: 日本
   Country Name (2 letter code) [JP]: JP
   # Tokyo:東京都
   State or Province Name (full name) [Tokyo]: Tokyo
   # Setagaya:世田谷区
   Locality Name (eg, city) [Setagaya]: Setagaya
   # 会社名 or ドメイン名
   Organization Name (eg, company) [example.com]: example.com
   # 部署などであるが、ここではそのままリターン
   Organizational Unit Name (eg, section) []: 
   # サイト名--ウェブサーバーで利用する場合は必ずここをサイト名に合わせる
   Common Name (eg, your name or your server's hostname) []: www.exmple.com
   # 管理者メールアドレス
   Email Address [webmaster@example.com]: webmaster@example.com
   
   Please enter the following 'extra' attributes
   to be sent with your certificate request
   A challenge password []:        # そのままリターン
   An optional company name []:    # そのままリターン
   Request is in newreq.pem, private key is in newkey.pem
   
   # ここで作成したウェブサーバー用の各ファイルは、カレントディレクトリに以下のように作成されます。
   $ ls -1
   ...
   newkey.pem  # サイト用秘密鍵
   newreq.pem  # サイト用公開鍵-署名なし（CSR）
   ```

6. クライアント用証明書を作成

   ```sh
   $ cd /etc/pki/tls/
   $ DAYS="-days 3650" SSLEAY_CONFIG="-config /etc/pki/tls/openssl-client.cnf" /etc/pki/tls/misc/CA -newreq
   ...
   
   Generating a 2048 bit RSA private key
   ......+++
   ..........+++
   writing new private key to 'newkey.pem'
   # クライアント用のパスフレーズ（パスワード）を入力します。
   Enter PEM pass phrase: 
   # クライアント用のパスフレーズ（パスワード）を再入力します。
   Verifying - Enter PEM pass phrase: 
   -----
   You are about to be asked to enter information that will be incorporated
   into your certificate request.
   What you are about to enter is what is called a Distinguished Name or a DN.
   There are quite a few fields but you can leave some blank
   For some fields there will be a default value,
   If you enter '.', the field will be left blank.
   -----
   # デフォルト([]内表示)で良い場合は、そのままRETURNでOKです。
   # JP: 日本
   Country Name (2 letter code) [JP]: JP
   # Tokyo:東京都
   State or Province Name (full name) [Tokyo]: Tokyo
   # Setagaya:世田谷区
   Locality Name (eg, city) [Setagaya]: Setagaya
   # 会社名 or ドメイン名
   Organization Name (eg, company) [example.com]: example.com
   # 部署などであるが、ここではそのままリターン
   Organizational Unit Name (eg, section) []: 
   # クライアント名
   Common Name (eg, your name or your server's hostname) []: Taro Yamada
   # メールアドレス
   Email Address [webmaster@example.com]: taro.yamada@example.com
   
   Please enter the following 'extra' attributes
   to be sent with your certificate request
   # そのままリターン
   A challenge password []: 
   # そのままリターン
   An optional company name []: 
   Request is in newreq.pem, private key is in newkey.pem
   
   # ここで作成したウェブクライアント用の各ファイルは、カレントディレクトリに以下のように作成されます。
   $ ls -1
   ...
   newkey.pem  # クライアント用秘密鍵
   newreq.pem  # クライアント用公開鍵-署名なし（CSR）
   ...
   ```

   

7. 認証局の署名を入れたクライアント証明書を作成

   ```sh
   # 次に認証局の署名を入れたクライアント証明書を作成します。
   $ cd /etc/pki/tls/return
   $ SSLEAY_CONFIG="-config /etc/pki/tls/openssl-client.cnf" /etc/pki/tls/misc/CA -signreturn
   ...
   
   Using configuration from /etc/.../openssl.cnf
   # CA証明書を作成する際に設定したパスフレーズ（パスワード）を入力する
   Enter pass phrase for /etc/.../CA/private/cakey.pem: return
   Check that the request matches the signature
   Signature ok
   Certificate Details:
           Serial Number:
               b7:be:81:5e:d6:43:63:b2
           Validity
               Not Before: Nov 19 19:43:12 2012 GMT
               Not After : Nov 19 19:43:12 2013 GMT
           Subject:
               countryName               = JP
               stateOrProvinceName       = Tokyo
               localityName              = Setagaya
               organizationName          = example.com
               commonName                = 192.168.1.99
               emailAddress              = webmaster@example.com
           X509v3 extensions:
               X509v3 Basic Constraints:
                   CA:FALSE
               Netscape Comment:
                   OpenSSL Generated Certificate
               X509v3 Subject Key Identifier:
                   4A:  ...
               X509v3 Authority Key Identifier:
                   keyid:8A: ...
   
   Certificate is to be certified until Nov 19 19:43:12 2013 GMT (3650 days)
   # yを入力しリターン
   Sign the certificate? [y/n]: yreturn
   
   # yを入力しリターン
   1 out of 1 certificate requests certified, commit? [y/n] yreturn
   Write out database with 1 new entries
   Data Base Updated
   Certificate:
       Data:
           Version: 3 (0x2)
           Serial Number:
               b7:be:81:5e:d6:43:63:b2
           Signature Algorithm: sha1WithRSAEncryption
           Issuer: C=JP, ST=Tokyo, O=example.com, CN=Private CA/emailAddress=webmaster@example.com
           Validity
               Not Before: Nov 19 19:43:12 2012 GMT
               Not After : Nov 19 19:43:12 2013 GMT
           Subject: C=JP, ST=Tokyo, L=Setagaya, O=example.com, CN=www.example.com/emailAddress=webmaster@example.com
           Subject Public Key Info:
               Public Key Algorithm: rsaEncryption
                   Public-Key: (2048 bit)
                   Modulus:
                       00:ab:57:e2:10:35:e1:54:0e:93:be: ...
                       ...
                       53:2f
                   Exponent: 65537 (0x10001)
           X509v3 extensions:
               X509v3 Basic Constraints:
                   CA:FALSE
               Netscape Comment:
                   OpenSSL Generated Certificate
               X509v3 Subject Key Identifier:
                   4A:23:33:A0:8E:BC:C2:E4:E1:69:B0:4C:B6:81:89:99:FE:62:3E:76
               X509v3 Authority Key Identifier:
                   keyid:8A:3A:EF:15:39:09:6B:5E:07:F5:E5:4B:FB:19:F9:00:D6:E3:82:EA
   
       Signature Algorithm: sha1WithRSAEncryption
           90:66:90:e1:47:3f:da:00:08:68:8d:97 ...
           ...
           a5:2d:c6:95
   -----BEGIN CERTIFICATE-----
   MIID8DCCAtigAwIBAgIJALe+gV7WQ2OyMA0GCSqGSIb3DQEB ...
   ...
   pS3GlQ==
   -----END CERTIFICATE-----
   Signed certificate is in newcert.pem
   
   # ここで作成したウェブクライアント用のクライアント証明書は、カレントディレクトリに以下のように作成されます。
   $ ls -1return
   ...
   newcert.pem # クライアント証明書（プライベート認証局の署名入り）
   ```

   

8. 作成したクライアント用証明書、秘密鍵をPKCS#12フォーマットに変換

   ```sh
   $ openssl pkcs12 -export -in newcert.pem -inkey newkey.pem -out taro.yamada.example.com.pfxreturn
   
   Enter pass phrase for newkey.pem:    # クライアント証明書を作成した時のパスフレーズ
   Enter Export Password:               # ここで作成したPCL12ファイルを展開（読み込む）するためのパスフレーズ 
   Verifying - Enter Export Password:   # --再入力
   ```

   

9. （必要に応じて）作成したクライアント用証明書、秘密鍵をバックアップ

   ```sh
   mv /etc/pki/tls/newcert.pem /etc/pki/CA/client/certs/fronteo.bil.com.crt
   mv /etc/pki/tls/newkey.pem /etc/pki/CA/client/private/fronteo.bil.com.key
   mv /etc/pki/tls/fronteo.bil.com.pfx /etc/pki/CA/client/private/fronteo.bil.com.pfx
   ```

   

10. (必要に応じて）PEM形式の証明書を作成

    ```sh
    openssl x509 -in /etc/pki/CA/client/certs/fronteo.bil.com.crt -outform PEM -out /etc/pki/CA/client/certs/fronteo.bil.com.pem
    ```

    

11. a