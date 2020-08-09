#!/bin/sh

INSPECT_DIR=/var/spool/filter
SENDMAIL="/usr/sbin/sendmail -i"

#whoami
#ls -l $INSPECT_DIR > /tmp/logs/result.txt
echo "Hello, contents-filter.3"
cd $INSPECT_DIR
cat >in.$$
echo <in.$$

exit $?
