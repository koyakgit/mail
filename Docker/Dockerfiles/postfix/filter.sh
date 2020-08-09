#!/bin/sh

INSPECT_DIR=/var/spool/filter
#SENDMAIL="/usr/sbin/sendmail -i"

echo "Hello, contents-filter.8"
cat > $INSPECT_DIR/$(date +%Y%m%d_%H%M%S_%3N).eml

exit $?
