From alpine:3.7
ENV ANSIBLE_HOST_KEY_CHECKING=False
RUN apk add ansible gcc python3-dev libc-dev libffi-dev openssl-dev && \
    apk add --no-cache --update python3 && \
    pip3 install --upgrade pip setuptools
RUN pip3 install --upgrade paramiko
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]