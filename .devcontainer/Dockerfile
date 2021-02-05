# For development by VSCode

# Main stage -------------------------------------------------
FROM adoptopenjdk/openjdk11:alpine
LABEL Name=kk Version=0.0.1

# Localize
RUN apk update && \
    apk upgrade
RUN apk --no-cache add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata
ENV TZ Asia/Tokyo \
    LANG ja_JP.UTF-8 \
    LANGUAGE ja_JP.UTF-8 \
    LC_ALL ja_JP.UTF-8

RUN apk --no-cache add git
