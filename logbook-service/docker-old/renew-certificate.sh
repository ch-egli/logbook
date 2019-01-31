#!/bin/sh
echo "Renewing certificate..."
certbot renew --pre-hook "service nginx stop" --post-hook "service nginx start" >> /root/certbot-renewal.log
echo "Certificate renewal completed"