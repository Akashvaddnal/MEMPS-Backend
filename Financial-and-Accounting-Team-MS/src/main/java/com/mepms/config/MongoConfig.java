package com.mepms.config;


import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
 
@Configuration
public class MongoConfig {
 
    @Autowired
    private MappingMongoConverter mappingMongoConverter;
 
    // This method runs after bean initialization
    @PostConstruct
    public void setUp() {
        // Remove _class field from the Mongo documents globally
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
    
    @Bean
    public MongoCustomConversions customConversions() {
        Converter<String, LocalDateTime> stringToLocalDateTime = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (source == null || source.isEmpty()) {
                    return null;
                }
                return LocalDateTime.parse(source);
            }
        };
        
        return new MongoCustomConversions(Arrays.asList(stringToLocalDateTime));
    }
    
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean validator) {
        return new ValidatingMongoEventListener(validator);
    }
}