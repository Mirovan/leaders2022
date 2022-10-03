# Web интерфейс приложения

## About


## Сборка проекта и развертывание
Запуск приложение состоит из следующих стадий:
- Запуск контейнера с Postgres
- Запуск контейнера c миграцией через Liquibase
- Сборка через maven и запуск контейнера c Spring Boot приложением


Для сборки приложения и создания Docker-image необходимо запустить файл: backend_run.bat


Для сборки и развертывания необходимо последовательно выполнить команды:
- docker-compose build
- docker-compose run


Swagger:
- http://localhost:8080/v2/api-docs
- http://localhost:8080/swagger-ui/
