package com.khy.hex.kafka.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;


// Use this code snippet in your app.
// If you need more information about configurations or implementing the sample
// code, visit the AWS docs:
// https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html

@Slf4j
@Configuration
public class AwsMskSecretConfig {

    public static void getSecret() {

        String secretName = "AmazonMSK_ssp";
//        String awsAccessKeyId="ASIAVG37AUQCM5COELWY";
//        String secretAccessKey="avSNnwQHBZjpO9h3gAO1wB4l955Ijri4kllWtcfM";

        Region region = Region.of("ap-northeast-2");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                //.credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw e;
        }

        String secret = getSecretValueResponse.secretString();
        log.info(">>> secret :: {}", secret);

        // Your code goes here.
    }
}
