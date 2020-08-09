#!/bin/sh

INSPECT_DIR=/var/spool/filter
SENDMAIL="/usr/sbin/sendmail -i"

ls -l $INSPECT_DIR > /usr/local/bin/result.txt

exit $?
