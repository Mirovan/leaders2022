Поднятие сервиса
````
sudo nohup java -jar /opt/bigint/web-0.0.1-SNAPSHOT.jar --spring.config.location=file:///opt/bigint/application-prom.properties &
````

Остановка сервиса 
````
ps aux | grep java
kill -9 <PID>
````
Смена пользователя 
````
sudo -i -u bigint
````

Install PostGIS
````
CREATE EXTENSION postgis;
````

Enable PostGIS
````
CREATE EXTENSION postgis;
-- enable raster support (for 3+)
CREATE EXTENSION postgis_raster;
-- Enable Topology
CREATE EXTENSION postgis_topology;
-- Enable PostGIS Advanced 3D
-- and other geoprocessing algorithms
-- sfcgal not available with all distributions
CREATE EXTENSION postgis_sfcgal;
-- fuzzy matching needed for Tiger
CREATE EXTENSION fuzzystrmatch;
-- rule based standardizer
CREATE EXTENSION address_standardizer;
-- example rule data set
CREATE EXTENSION address_standardizer_data_us;
-- Enable US Tiger Geocoder
CREATE EXTENSION postgis_tiger_geocoder;
````

Установка SQL-скриптов
````
psql -d leaders2022postamat -f /opt/bigint/files/houses.sql
````

Удаление схемы БД
````
sudo -i -u postgres psql
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
\q
````

Просмотр таблиц
````
psql -d leaders2022postamat -U bigint
````

Просмотр таблиц
````
psql -d leaders2022postamat
\dt public.*
````