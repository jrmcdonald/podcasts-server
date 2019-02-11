FROM python:alpine

RUN apk --no-cache add \
        ffmpeg \
        findutils

COPY ./src/main/resources/youtube-dl.conf /etc/youtube-dl.conf
COPY ./src/main/resources/crontab /crontab

RUN crontab /crontab

COPY ./src/main/resources/requirements.txt /app/requirements.txt
COPY ./src/main/sh/download.sh /app/download.sh
WORKDIR /app

RUN chmod +x download.sh
RUN pip install -r requirements.txt

CMD [ "crond", "-f" ]