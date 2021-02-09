package de.schichttauschen.web.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
public class PublicMessagesController {
    private final static Map<String, String> messages = new HashMap<>();

    @SneakyThrows
    @PostConstruct
    private void loadMessages() {
        var properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/messages.properties"));
        properties.keySet().stream().map(Object::toString).forEach(key -> messages.put(key, properties.getProperty(key)));
    }

    @GetMapping("/api/public/messages")
    public Map<String, String> register() {
        return messages;
    }
}