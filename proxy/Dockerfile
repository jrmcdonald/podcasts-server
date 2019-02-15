FROM nginx:alpine

RUN apk --no-cache add \
        apache2-utils 

WORKDIR /etc/nginx

RUN rm conf.d/default.conf
COPY src/main/resources/conf.d/* conf.d/