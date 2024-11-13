package com.back.cake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;



@SpringBootApplication
public class CakeApplication {

	public static void main(String[] args) {


 String host = "jdbc:mysql://mysqlfirstservice-primeiro-projetojava.i.aivencloud.com:24844/cakereceitas";
        
        // Verifica se está em ambiente de produção (Render)
        boolean isProduction = System.getenv("RENDER") != null;
        
        String uname;
        String upass;

        if (isProduction) {
            // Em produção, obtém as variáveis diretamente do ambiente
            uname = System.getenv("USER_NAME");
            upass = System.getenv("PASSWORD_NAME");
        } else {
            // Em desenvolvimento, usa o arquivo .env
            Dotenv dotenv = Dotenv.load();
            uname = dotenv.get("USER_NAME");
            upass = dotenv.get("PASSWORD_NAME");
        }



		SpringApplication.run(CakeApplication.class, args);
	}

}
