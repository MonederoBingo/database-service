@echo off
cd %~dp0
call mvn clean install -DskipTests
call java -jar target/database-0.0.1-SNAPSHOT.jar
