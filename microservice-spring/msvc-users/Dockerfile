#Primero se debe crear la imagen base de Docker con el comando:
FROM openjdk:18.0.2

#seguno se agrega nuestra carpeta de trabajo
WORKDIR /app

#Tercero se copian los archivos necesarios para la compilacion
#al colocar . se copian todos los archivos del directorio actual (WORKDIR)
COPY ./target/msvc-users-0.0.1-SNAPSHOT.jar .

#Cuarto se ejecuta el comando para iniciar la aplicacion en el puerto 8001
EXPOSE 8001

#Por ultimo se define el comando que se ejecutara al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"]

