package com.example.clientimplicit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class ClientImplicitApplication implements ServletContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ClientImplicitApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        servletContext.getSessionCookieConfig().setName("client-session");
    }

}
