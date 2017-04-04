#!/bin/bash
source /env.sh
if test -z "$CODELAB_PASS"; then
    echo "CODELAB_PASS not defined"
    exit 1
fi

auth="-u user -p $CODELAB_PASS"

# MONGODB USER CREATION
(
echo "setup mongodb auth"
create_user="if (!db.getUser('user')) { db.createUser({ user: 'user', pwd: '$CODELAB_PASS', roles: [ {role:'readWrite', db:'codelab'} ]}) }"
until mongo codelab --eval "$create_user" || mongo codelab $auth --eval "$create_user"; do sleep 5; done
killall mongod
sleep 1
killall -9 mongod
) &

# INIT DUMP EXECUTION
(
if test -n "$INIT_DUMP"; then
    echo "execute dump file"
	until mongo codelab $auth $INIT_DUMP; do sleep 5; done
fi
) &

echo "start mongodb without auth"
chown -R mongodb /data/db
gosu mongodb mongod "$@"

echo "restarting with auth on"
sleep 5
exec gosu mongodb mongod --auth "$@"