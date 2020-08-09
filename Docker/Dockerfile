# For development by VSCode

# Main stage -------------------------------------------------
FROM centos:8.1.1911
#FROM centos:latest
LABEL Name=FRONTEO Version=0.0.1

# Localize ===============================================
RUN dnf -y update && \
    dnf -y install glibc-locale-source glibc-langpack-ja && \
    localedef -i /usr/share/i18n/locales/ja_JP -f UTF-8 /usr/lib/locale/ja_JP.UTF-8
ENV TZ=Asia/Tokyo LANG=ja_JP.UTF-8 LANGUAGE=ja_JP:ja LC_ALL=ja_JP.UTF-8

# Install Postfix =========================================
RUN dnf install -y postfix dovecot mailx passwd lsof
ENV POSTFIX_HOME=/etc/postfix FILTER_HOME=/home/filter SPOOL_DIR=/var/spool/filter

#   メールボックスはMaildirを参照するように設定
RUN echo 'export MAIL=$HOME/Maildir' >> /etc/profile.d/mail.sh && \
    useradd cent && \
    useradd filter && \
    mkdir $SPOOL_DIR && \
    mkdir $FILTER_HOME/script
    
COPY ./Dockerfiles/postfix/main.cf $POSTFIX_HOME/.
COPY ./Dockerfiles/postfix/master.cf $POSTFIX_HOME/.
COPY ./Dockerfiles/postfix/filter.sh $FILTER_HOME/script/.

RUN chown -R filter:filter $SPOOL_DIR && \
    chmod -R 700 $SPOOL_DIR && \
    chown -R filter:filter $FILTER_HOME && \
    chmod -R 777 $FILTER_HOME

# postfixが参照できるようにする

# COPY ./Dockerfiles/postfix/relay_recipients /etc/postfix/.
# COPY ./Dockerfiles/postfix/transport /etc/postfix/.
# Postfix動作用のエイリアス設定＋コンテンツフィルタで使用する疑似ユーザの作成（シェル実行不可）
#RUN newaliases && \
#    useradd -s /sbin/nologin filter && \
#    mkdir -p /var/spool/filter/ && \
#    chown -R filter.filter /var/spool/filter && \
#    mkfifo /var/spool/postfix/public/pickup
    
# POP/IMAPサーバの設定
COPY ./Dockerfiles/dovecot/dovecot.conf /etc/dovecot/.
COPY ./Dockerfiles/dovecot/10-*.conf /etc/dovecot/conf.d/

RUN systemctl enable postfix && \
    systemctl enable dovecot
#    systemctl enable dovecot && \
#    systemctl enable saslauthd
# EXPOSE 25 80 993 995 587 10025

# COPY ./Dockerfiles/start.sh /usr/local/etc/start.sh
# RUN chmod +x /usr/local/etc/start.sh
# ENTRYPOINT [ "sh", "/usr/local/etc/start.sh" ]