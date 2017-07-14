release: java -cp build/classes/main:build/dependencies/*:build/resources/main com.monederobingo.database.migrations.Migrate
web: java $JAVA_OPTS -Dserver.port=$PORT -Deureka.client.service-url.defaultZone=https://mb-discovery-service.herokuapp.com/eureka/ -jar app.jar
