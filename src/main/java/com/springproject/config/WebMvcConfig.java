package com.springproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springproject.impl.json.JSONMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter)
                .forEach(c -> ((MappingJackson2HttpMessageConverter) c).setObjectMapper(JSONMapper.OBJECT_MAPPER));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JSONMapper.OBJECT_MAPPER;
    }
}
