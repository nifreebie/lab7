FROM postgres:latest


ENV POSTGRES_DB=lab7db
ENV POSTGRES_USER=nifreebie
ENV POSTGRES_PASSWORD=1234


COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 5432