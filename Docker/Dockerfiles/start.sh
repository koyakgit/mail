#!/bin/sh

# execute Postfix

declare -i PROCESS_COUNT
while true
do
  # PROCESS_COUNT=`ps -ef | grep postfix | wc -l`
  # if [ $PROCESS_COUNT -le 1 ]; then
  #   /usr/sbin/postfix start
  #   echo "Postfix is started."
  # fi
  sleep 5
done