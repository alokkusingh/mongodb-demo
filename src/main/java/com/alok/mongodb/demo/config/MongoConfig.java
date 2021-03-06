package com.alok.mongodb.demo.config;

import com.alok.mongodb.demo.codec.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "DEMODB";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/DEMO_DB");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.alok.mongodb.demo");
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(asList(
                new YearMonthToStringConverter(),
                new StringToYearMonthConverter(),
                new DocumentToYearMonthConverter(),
                new ZonedDateTimeToDocumentConverter(),
                new DocumentToZonedDateTimeConverter()
        ));
    }
}
