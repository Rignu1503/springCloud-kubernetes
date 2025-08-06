#Primero se debe crear la imagen base de Docker con el comando:
FROM openjdk:18-jdk-alpine AS builder

#Creamos nuestro directorio de trabajo(todos los comandos siguientes se ejecutaran en este directorio)
WORKDIR /app/msvc-users

#Copiamois el pom.xml de directorio raiz del proyecto a la carpeta de trabajo "microservice spring" porque msvc-users dependia de el
COPY ./pom.xml /app
#Copiamos el wrapper de maven y lo colocamos en la carpeta de trabajo(WOrkdir)
COPY ./msvc-users/.mvn ./.mvn
#Copiamos el archivo mvnw
COPY ./msvc-users/mvnw .
COPY ./msvc-users/pom.xml .

#creamos el jar de nuestro (RUn ejecuta el comando en el contenedor)
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./msvc-users/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:18-jdk-alpine

#Segundo se crea el directorio de trabajo
WORKDIR /app

COPY --from=builder /app/msvc-users/target/msvc-users-0.0.1-SNAPSHOT.jar .

#Cuarto se ejecuta el comando para iniciar la aplicacion en el puerto 8001
EXPOSE 8001

#Por ultimo se define el comando que se ejecutara al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"]

