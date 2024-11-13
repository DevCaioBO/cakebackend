package com.back.cake.configCake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class appConfig {
    private final String uname;
    private final String upass;

    public appConfig() {
        // Verifica se está em ambiente de produção (Render)
        boolean isProduction = System.getenv("RENDER") != null;

        if (isProduction) {
            // Em produção, obtém as variáveis diretamente do ambiente
            this.uname = System.getenv("USER_NAME");
            this.upass = System.getenv("PASSWORD_NAME");
            System.out.println("Produção - uname: " + this.uname + ", upass: " + this.upass);
        } else {
            // Em desenvolvimento, usa o arquivo .env
            Dotenv dotenv = Dotenv.load();
            this.uname = dotenv.get("USER_NAME");
            this.upass = dotenv.get("PASSWORD_NAME");
            System.out.println("Desenvolvimento - uname: " + this.uname + ", upass: " + this.upass);
        }
    }

    @Bean
    public String getUname() {
        return uname;
    }

    @Bean
    public String getUpass() {
        return upass;
    }
}
