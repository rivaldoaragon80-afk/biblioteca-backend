package com.example.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> details = new HashMap<>();
        details.put("app", "Biblioteca Backend");
        details.put("version", "1.0.0");
        details.put("description", "Sistema de gestión de biblioteca con préstamos de libros");
        details.put("java-version", System.getProperty("java.version"));
        
        builder.withDetail("application", details);
    }
}
