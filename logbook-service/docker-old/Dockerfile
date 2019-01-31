FROM postgres:11.1
MAINTAINER Christian Egli <christian.egli@gmx.net>

# Avoid ERROR: invoke-rc.d: policy-rc.d denied execution of start.
#
RUN echo "#!/bin/sh\nexit 0" > /usr/sbin/policy-rc.d

# install jdk-8, supervisor, python (for installing pip and current aws) and nano
#
RUN echo 'deb http://ftp.debian.org/debian jessie-backports main' >> /etc/apt/sources.list
RUN apt-get update && apt-get install -y supervisor && apt-get -t jessie-backports install -y openjdk-8-jdk && apt-get install -y python && apt-get install -y nano && apt-get install -y nginx

# is necessary for nano to work properly
#
ENV TERM xterm

# install aws cli (in debian jessy there is only a very old version available)
#
COPY get-pip.py /
RUN python get-pip.py
RUN pip install awscli
RUN mkdir -p /root/.aws
COPY config /root/.aws/
COPY credentials /root/.aws/

# configure database backup script and directory
#
RUN mkdir -p /root/backups
COPY backup.sh /root/

# add cron script for backup
#
RUN echo "07 01 * * * root  /root/backup.sh" >> /etc/crontab

COPY renew-certificate.sh /root/renew-certificate.sh
RUN chmod 775 /root/renew-certificate.sh
RUN echo "13 02 * * * root  /root/renew-certificate.sh" >> /etc/crontab


# supervisor: create directory for logs and copy config file
#
RUN mkdir -p /var/log/supervisor
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# install certbot (let's encrypt)
#
RUN echo 'deb http://ftp.debian.org/debian stretch main' >> /etc/apt/sources.list
RUN apt-get update && apt-get install -y -t stretch python-cffi-backend python-cryptography python-openssl
### RUN apt-get install -y certbot -t jessie-backports
RUN apt-get install -y wget curl
RUN wget https://dl.eff.org/certbot-auto
RUN chmod a+x certbot-auto

RUN unlink /etc/nginx/sites-enabled/default
RUN mkdir /var/www/logbook.snoopfish.ch
COPY logbook.snoopfish.ch.conf /etc/nginx/conf.d/logbook.snoopfish.ch.conf
COPY logbook.snoopfish.ch.conf.init /etc/nginx/conf.d/logbook.snoopfish.ch.conf.init

# see https://stackoverflow.com/questions/43323754/cannot-make-remove-an-entry-for-the-specified-session-cron
RUN sed -i '/session    required     pam_loginuid.so/c\#session    required   pam_loginuid.so' /etc/pam.d/cron

EXPOSE 80 443 8080

# install logbook application
#
COPY logbook.jar /

CMD ["/usr/bin/supervisord"]
