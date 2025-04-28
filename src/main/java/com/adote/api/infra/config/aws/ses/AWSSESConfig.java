package com.adote.api.infra.config.aws.ses;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AWSSESConfig {

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        String emailProfile = "adote";

        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider(emailProfile))
                .withRegion(Regions.SA_EAST_1)
                .build();
    }
}
