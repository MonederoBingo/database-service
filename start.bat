@echo off
cd %~dp0
call gradlew -x test
call java -jar build/libs/database-service-0.0.1-SNAPSHOT.jar
