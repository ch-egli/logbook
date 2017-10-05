FROM postgres:9.4.6
MAINTAINER Christian Egli <christian.egli@gmx.net>

# Avoid ERROR: invoke-rc.d: policy-rc.d denied execution of start.
#
RUN echo "#!/bin/sh\nexit 0" > /usr/sbin/policy-rc.d

# install jdk-8, supervisor, python (for installing pip and current aws) and nano
#
RUN echo 'deb http://ftp.debian.org/debian jessie-backports main' >> /etc/apt/sources.list
RUN apt-get update && apt-get install -y supervisor && apt-get -t jessie-backports install -y openjdk-8-jdk && apt-get install -y python && apt-get install -y nano

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

# supervisor: create directory for logs and copy config file
#
RUN mkdir -p /var/log/supervisor
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf 

# install certbot (let's encrypt)
#
RUN echo 'deb http://ftp.debian.org/debian stretch main' >> /etc/apt/sources.list
RUN apt-get update && apt-get install -y -t stretch python-cffi-backend python-cryptography python-openssl
RUN apt-get install -y certbot -t jessie-backports

EXPOSE 8181 80 443

# install logbook application
#
COPY logbook.jar /

CMD ["/usr/bin/supervisord"]
