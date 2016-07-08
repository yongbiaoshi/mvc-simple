package com.tsingda.simple.config;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "{app.base.package}.dao.repository", repositoryImplementationPostfix = "Impl", queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
public class MongoDBConfig extends AbstractMongoConfiguration {

    @Value("${mongo.database}")
    private String database;

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private int port;

    @Value("${app.base.package}")
    private String appBasePackage;

    private String basePackage = "model.mongo";

    @PostConstruct
    public void init() {
        this.basePackage = this.appBasePackage + "." + basePackage;
    }

    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient(host, port);
    }

    @Bean
    public Mongo mongo() throws Exception {
        return mongoClient();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

    @Override
    public String getDatabaseName() {
        return this.database;
    }

    @Override
    public String getMappingBasePackage() {

        return basePackage;
    }

    @Bean
    @Override
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        return new CustomConversions(converterList);
    }

    @Bean
    public LoggingEventListener mappingEventsListener() {
        return new LoggingEventListener();
    }

    @Bean
    public MongoTypeMapper customTypeMapper() {
        return new DefaultMongoTypeMapper();
    }

}
