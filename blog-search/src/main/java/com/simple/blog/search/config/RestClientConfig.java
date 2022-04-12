package com.simple.blog.search.config;

import com.simple.blog.common.properties.ElasticsearchProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * Elasticsearch 客户端配置
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchProperties.getUri())
                .withBasicAuth(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword())
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}
