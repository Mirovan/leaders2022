FROM liquibase/liquibase:latest

#ENV LIQUIBASE_URL=jdbc:postgresql://127.0.0.1:5432/webapp_db
#ENV LIQUIBASE_USERNAME=postgres
#ENV LIQUIBASE_PASSWORD=postgres
#ENV LIQUIBASE_CHANGELOG=./db.changelog.xml

COPY ./liquibase /liquibase
COPY ./wait-for-it.sh /wait-for-it.sh
COPY ./liquibase_run.sh /liquibase/liquibase_run.sh

#run as single docker-container
#CMD ["sh", "-c", "docker-entrypoint.sh --url=${LIQUIBASE_URL} --username=${LIQUIBASE_USERNAME} --password=${LIQUIBASE_PASSWORD} --classpath=/liquibase/classpath --changeLogFile=${LIQUIBASE_CHANGELOG} update"]