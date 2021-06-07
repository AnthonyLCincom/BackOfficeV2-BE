package com.lcincom.bo.configuration;

import com.lcincom.bo.model.Form;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;
import java.util.Properties;

@Configuration
public class DataImport {

    @Bean
    public CommandLineRunner initData(MongoOperations mongo) {
        return (String... args) -> {
            mongo.dropCollection(Form.class);
            mongo.createCollection(Form.class);
            getShows().forEach(mongo::save);
        };
    }

    private List<Form> getShows() {
        Properties yaml = loadShowsYaml();
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(yaml);
        return new Binder(source).bind("form", Bindable.listOf(Form.class)).get();
    }

    private Properties loadShowsYaml() {
        YamlPropertiesFactoryBean properties = new YamlPropertiesFactoryBean();
        properties.setResources(new ClassPathResource("form.yml"));
        return properties.getObject();
    }

}
