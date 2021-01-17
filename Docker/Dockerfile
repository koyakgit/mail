# For Mail filtering test

# Main stage -------------------------------------------------
FROM centos:8.1.1911
LABEL Name=FRONTEO Version=0.0.1

# Localize ===============================================
RUN dnf -y update && \
    dnf -y install glibc-locale-source glibc-langpack-ja && \
    localedef -i /usr/share/i18n/locales/ja_JP -f UTF-8 /usr/lib/locale/ja_JP.UTF-8
ENV TZ=Asia/Tokyo LANG=ja_JP.UTF-8 LANGUAGE=ja_JP:ja LC_ALL=ja_JP.UTF-8

# Install openssl
# 証明書作成のため、opensslをインストール
# バージョン、環境確認方法
# openssl version -a
RUN dnf -y install openssl

# CAシェルの用意
RUN dnf -y install wget
WORKDIR /tmp
RUN wget http://ftp.riken.jp/Linux/centos/7/os/x86_64/Packages/openssl-1.0.2k-19.el7.x86_64.rpm
RUN rpm2cpio openssl-1.0.2k-19.el7.x86_64.rpm | cpio -id ./etc/pki/tls/misc/CA
RUN mv -i etc/pki/tls/misc/CA /etc/pki/tls/misc/
RUN rm -rf etc && rm -f openssl*
WORKDIR /

# opensslの設定更新
COPY Dockerfiles/openssl.cnf /etc/pki/tls/openssl.cnf
WORKDIR /etc/pki/tls
COPY Dockerfiles/openssl-ca.cnf .
COPY Dockerfiles/openssl-server.cnf .
COPY Dockerfiles/openssl-client.cnf .

# 受信サーバのインストール
RUN dnf -y install dovecot
COPY Dockerfiles/dovecot.conf /etc/dovecot/dovecot.conf
COPY Dockerfiles/10-mail.conf /etc/dovecot/conf.d/10-mail.conf
RUN systemctl enable dovecot
