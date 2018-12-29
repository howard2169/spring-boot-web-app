package com.springproject.impl.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

public class SpringAppBuilder {

    private final SpringApplicationBuilder builder;

    public SpringAppBuilder(Class<?>... sources) {
        this.builder = new SpringApplicationBuilder(sources);
    }

    public SpringApplication build() {
        SpringApplication app = builder.build();
        app.addListeners(new ApplicationPidFileWriter());
        return app;
    }
}
