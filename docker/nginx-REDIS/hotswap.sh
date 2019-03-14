#!/bin/bash

CONF_FILE="/etc/nginx/nginx.conf"
if grep -q $1 "$CONF_FILE"; then
  sed -i 's/redis:\|[0-9.]\{12\}:/'"$1"':/g' "$CONF_FILE"
  /usr/sbin/nginx -s reload
fi