package com.plueone.server.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    @Value("${DO_STORAGE_KEY}")
    private String accessKey;

    @Value("${DO_STORAGE_SECRETKEY}")
    private String secretKey;

    @Value("${DO_STORAGE_ENDPOINT}")
    private String endpoint;

    @Value("${DO_STORAGE_REGION}")
    private String endPointRegion;

    @Bean
    public AmazonS3 createS3Client() {
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);

        EndpointConfiguration ep = new EndpointConfiguration(endpoint, endPointRegion);

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(ep)
                .withCredentials(new AWSStaticCredentialsProvider(cred))
                .build();
    }

    @Bean
    public WebMvcConfigurer configureCors() {
        return new EnableCors("/**", "http://localhost:4200");
    }

}
