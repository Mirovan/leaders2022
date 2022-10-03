call mvn clean package
call docker build -t mirovan/innotech_hack .
@REM call docker tag mirovan/innotech_hack store/name
@REM call docker push store/name
call docker run -dp 8080:8080 mirovan/innotech_hack