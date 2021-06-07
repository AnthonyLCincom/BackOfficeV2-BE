//package com.lcincom.accessControl.configuration;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
//import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//
//@Configuration
//@ComponentScan(basePackages = { "com.lcincom.accessControl.service" })
//@EnableAutoConfiguration(exclude={ElasticsearchDataAutoConfiguration.class})
//public class ElasticSearchConfig {
//
//    @Value("${elasticsearch.hostAndPort}")
//    private String hostAndPort;
//
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(hostAndPort)
//                .build();
//        return RestClients.create(clientConfiguration).rest();
//    }
//
////    @Bean
////    ReactiveElasticsearchClient client() {
////
////        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
////                .connectedTo(hostAndPort)
////                .build();
////
////        return ReactiveRestClients.create(clientConfiguration);
////    }
//
////    @Lazy
////    @Bean
////    public ElasticsearchOperations elasticsearchTemplate() {
////        return new ElasticsearchRestTemplate(elasticsearchClient());
////    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchRestTemplate(elasticsearchClient());
//    }
//
//
//}

package com.lcincom.bo.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
@ComponentScan(basePackages = {"com.lcincom.bo.service"})
@EnableAutoConfiguration(exclude={ElasticsearchDataAutoConfiguration.class})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.hostAndPort}")
    private String hostAndPort;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean(name = "elasticsearchRestTemplate")
    public ElasticsearchOperations elasticsearchTemplateCustom() {
        ElasticsearchRestTemplate elasticsearchTemplate;
        try {
            elasticsearchTemplate = new ElasticsearchRestTemplate(elasticsearchClient());
            return elasticsearchTemplate;
        } catch (Exception e) {
            return new ElasticsearchRestTemplate(elasticsearchClient());
        }
    }
}

