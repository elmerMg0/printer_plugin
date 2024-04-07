# README: Ejecutar un Proyecto de Spring Boot

## Descripción del Proyecto
Este proyecto es un plugin desarrollado en Spring Boot que permite recibir información en formato JSON a través del protocolo HTTP para posteriormente imprimir dicha información.

## Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:
- Java JDK 8 o superior
- Maven

## Instrucciones de Ejecución

1. **Clonar el Repositorio:**
   Clona el repositorio desde GitHub utilizando el siguiente comando:
   ```bash
   git clone https://github.com/elmerMg00/printer_plugin.git
   ```

2. **Compilar el Proyecto:**
   Ingresa al directorio del proyecto y ejecuta el siguiente comando para compilarlo:
   ```bash
   mvn clean package
   ```

3. **Ejecutar la Aplicación:**
   Una vez compilado, puedes ejecutar la aplicación Spring Boot utilizando el siguiente comando:
   ```bash
   mvn spring-boot:run 
   ```

4. **Acceder a la Aplicación:**
   La aplicación estará disponible en el siguiente enlace:
   ```
   http://localhost:9090/v1/printer
   ```

## Uso del Plugin
El plugin está diseñado para recibir información en formato JSON a través de peticiones HTTP POST en la ruta `/api/print`. Puedes enviar la información en JSON utilizando herramientas como Postman o cURL. Por ejemplo:
```bash
```
