FROM python:alpine

RUN apk --no-cache add \
        ffmpeg \
        findutils

COPY ./config/youtube-dl.conf /etc/youtube-dl.conf
COPY ./config/crontab /crontab

RUN crontab /crontab

COPY . /app
WORKDIR /app

RUN chmod +x download.sh
RUN pip install -r requirements.txt

CMD [ "crond", "-f" ]