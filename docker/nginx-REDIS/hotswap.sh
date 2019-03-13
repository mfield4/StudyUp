#!/bin/bash

sed -i 's/redis:\|[0-9.]{12}:/$1:/g' /etc/nginx/nginx.conf
/usr/sbin.nginx -s reload