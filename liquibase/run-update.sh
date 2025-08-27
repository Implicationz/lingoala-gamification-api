#!/bin/bash
set -e

# 📍 Move to the script's directory (ensures relative paths work)
cd "$(dirname "$0")"

# 🔐 Load environment variables from .env
set -o allexport
source .env
set +o allexport

# 🚀 Apply the changelog to Neon (prod)
liquibase \
  --changeLogFile=changelog/generated-changelog.xml \
  --url="$NEON_DB_URL" \
  --username="$NEON_DB_USER" \
  --password="$NEON_DB_PASS" \
  update
