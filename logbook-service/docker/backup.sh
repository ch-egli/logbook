#!/bin/bash

NOWDATE=`date +%Y-%m-%d--%H%M`
BACKUPNAME=/root/backups/$NOWDATE.sql

# Creating backup of database to $BACKUPNAME
pg_dump -U postgres -h localhost -d trainingdb -f $BACKUPNAME

# Uploading backup to Amazon S3 bucket…
/usr/local/bin/aws s3 cp $BACKUPNAME s3://che-climbing-logbook2

# One can wish to remove the backup file from the instance, using uncomment the below line
# rm $BACKUPNAME

echo Done
