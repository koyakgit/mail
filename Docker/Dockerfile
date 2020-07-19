# For development by VSCode

# Main stage -------------------------------------------------
FROM centos:8.1.1911
#FROM centos:latest
LABEL Name=FRONTEO Version=0.0.1

# Localize
##RUN dnf -y update
##RUN dnf -y upgrade
RUN dnf -y install glibc-locale-source glibc-langpack-ja && \
    localedef -i /usr/share/i18n/locales/ja_JP -f UTF-8 /usr/lib/locale/ja_JP.UTF-8
ENV TZ=Asia/Tokyo LANG=ja_JP.UTF-8 LANGUAGE=ja_JP:ja LC_ALL=ja_JP.UTF-8

# Install Postfix
RUN dnf install -y postfix passwd
COPY ./Dockerfiles/main.cf /etc/postfix/.
# COPY ./Dockerfiles/master.cf /etc/postfix/.
# Postfix動作用のエイリアス設定＋コンテンツフィルタで使用する疑似ユーザの作成（シェル実行不可）
RUN newaliases && \
    useradd -s /sbin/nologin filter && \
    mkdir -p /var/spool/filter/ && \
    chown -R filter.filter /var/spool/filter
# 不正なメール中継ができないよう、SASL機能を導入
RUN dnf -y install dovecot
COPY ./Dockerfiles/dovecot.conf /etc/dovecot/.
COPY ./Dockerfiles/10-*.conf /etc/dovecot/conf.d/

RUN systemctl enable postfix && \
    systemctl enable dovecot
EXPOSE 25 10025

# COPY ./Dockerfiles/start.sh /usr/local/etc/start.sh
# RUN chmod +x /usr/local/etc/start.sh
# ENTRYPOINT [ "sh", "/usr/local/etc/start.sh" ]