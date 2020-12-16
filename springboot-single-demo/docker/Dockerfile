############## Base Image with java #############
FROM amd64/openjdk

MAINTAINER wyg <wyg@evangtech.co.jp>

ENV SERVER_PORT=8080
ENV DB_HOST ""
ENV DB_PORT ""
ENV DB_USER ""

EXPOSE ${SERVER_PORT}

COPY ./demo-1.0.jar /demo-1.0.jar
COPY ./init.sh /init.sh
RUN /bin/bash -c "chmod 774 /init.sh"

ENTRYPOINT ["/init.sh"]