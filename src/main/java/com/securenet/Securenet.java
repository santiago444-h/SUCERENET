
package com.securenet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del proyecto SUCERENET.
 * Se encarga de iniciar la aplicación y poner en funcionamiento
 * el servicio web desarrollado con Spring Boot.
 */
@SpringBootApplication
public class Securenet {

    /**
     * Método principal de ejecución.
     * Inicia Spring Boot y el servidor web integrado Tomcat.
     */
    public static void main(String[] args) {

        // Inicia la aplicación y sus componentes.
        SpringApplication.run(Securenet.class, args);
    }
}
