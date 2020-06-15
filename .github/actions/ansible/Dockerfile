From alpine:3.7
ENV ANSIBLE_HOST_KEY_CHECKING=False
RUN apk add ansible gcc python3-dev libc-dev libffi-dev openssl-dev && \
    apk add --no-cache --update python3 && \
    apk add make && \
    # pip3 install --upgrade pip setuptools &&\
    python3 -m pip install -U pip
RUN pip3 install paramiko
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]