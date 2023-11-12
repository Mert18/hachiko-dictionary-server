#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "postgres" --dbname "hachiko-dictionary" -f /data/schema.sql
psql -v ON_ERROR_STOP=1 --username "postgres" --dbname "hachiko-dictionary" -f /data/data.sql