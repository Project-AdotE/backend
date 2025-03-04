package com.adote.api.infra.config.secrets;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class DataSourceConfig {

    @Autowired
    private AWSSecretManagerService awsSecretManagerService;

    @Bean
    public DataSource dataSource() {
        JsonNode secret = awsSecretManagerService.getSecret("adote-db-secret");

        String host = getJsonValue(secret, "host");
        String port = getJsonValue(secret, "port");
        String username = getJsonValue(secret, "username");
        String password = getJsonValue(secret, "password");

        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, "postgres");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    private String getJsonValue(JsonNode node, String key) {
        return Optional.ofNullable(node.get(key))
                .map(JsonNode::asText)
                .orElseThrow(() -> new IllegalStateException("Campo ausente no segredo: " + key));
    }
}
