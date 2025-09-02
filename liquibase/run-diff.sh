#!/bin/bash
set -e

# 📍 Move to the script's directory (ensures relative paths work)
cd "$(dirname "$0")"

# 🔐 Load environment variables from .env
set -o allexport
source .env
set +o allexport

# 📝 Generate changelog from local (updated) to Neon (prod) schema
liquibase \
  --changeLogFile=changelog/generated-changelog.xml \
  --url="$REMOTE_DB_URL" \
  --username="$REMOTE_DB_USER" \
  --password="$REMOTE_DB_PASS" \
  --referenceUrl="$LOCAL_DB_URL" \
  --referenceUsername="$LOCAL_DB_USER" \
  --referencePassword="$LOCAL_DB_PASS" \
  diffChangeLog
