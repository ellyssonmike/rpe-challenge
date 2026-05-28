#!/bin/bash
set -e

mongosh -u "$MONGO_INITDB_ROOT_USERNAME" -p "$MONGO_INITDB_ROOT_PASSWORD" --authenticationDatabase admin <<EOF

use $MONGODB_DATABASE;

db.createUser({
  user: "$MONGODB_DATABASE_USERNAME",
  pwd: "$MONGODB_DATABASE_PASSWORD",
  roles: [
    { role: "readWrite", db: "$MONGODB_DATABASE" },
    { role: "dbAdmin", db: "$MONGODB_DATABASE" }
  ]
});

EOF