FROM postgres:12-alpine

COPY postgresql.conf /etc/postgresql/postgresql.conf

RUN mkdir /var/log/postgresql \
    && chmod 0666 /var/log/postgresql \
    && chown postgres:postgres /var/log/postgresql

USER postgres

CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]
