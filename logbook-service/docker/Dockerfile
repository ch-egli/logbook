FROM postgres:11.1
MAINTAINER Christian Egli <christian.egli@gmx.net>

# Add debian testing repository and make the ‘stable’ repository default
RUN echo 'deb http://ftp.de.debian.org/debian testing main' >> /etc/apt/sources.list
RUN echo 'APT::Default-Release "stable";' | tee -a /etc/apt/apt.conf.d/00local
RUN apt-get update

RUN apt-get install -y supervisor && \
  apt-get install -y openjdk-8-jdk && \
  apt-get install -y nginx && \
  apt-get install -y nano && \
  apt-get install -y curl && \
  apt-get install -y -t testing python3-pip

RUN pip3 install awscli --upgrade
RUN mkdir -p /root/.aws
COPY config /root/.aws/
COPY credentials /root/.aws/

# install certbot to get certificate from letsencrypt
RUN echo 'deb http://ftp.debian.org/debian stretch-backports main' >> /etc/apt/sources.list
RUN apt-get update && apt-get install -y  -t stretch-backports python-certbot-nginx
COPY logbook.snoopfish.ch.conf /

# call once:
#   certbot --nginx
# afterwards copy the redirect configuration and restart nginx:
#   mv logbook.snoopfish.ch.conf /etc/nginx/conf.d/

# configure database backup script and directory
#
RUN mkdir -p /root/backups
COPY backup.sh /root/

# add cron script for backup
#
RUN echo "17 01 * * * root  /root/backup.sh" >> /etc/crontab

COPY renew-certificate.sh /root/renew-certificate.sh
RUN chmod 775 /root/renew-certificate.sh
RUN echo "17 02 * * * root  /root/renew-certificate.sh" >> /etc/crontab

# supervisor: create directory for logs and copy config file
#
RUN mkdir -p /var/log/supervisor
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

EXPOSE 80 443 8080

# fix locale error on postgres image
RUN sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen && \
        locale-gen
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# install logbook application
COPY logbook.jar /

CMD ["/usr/bin/supervisord"]
