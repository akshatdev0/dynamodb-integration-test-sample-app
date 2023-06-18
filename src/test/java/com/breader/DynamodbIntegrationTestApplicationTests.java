package com.breader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

@SpringBootTest
@Testcontainers
class DynamodbIntegrationTestApplicationTests {

    private static final DockerImageName LOCALSTACK_IMAGE_NAME = DockerImageName.parse("localstack/localstack:2.1.0-arm64");

    @Container
    private static final LocalStackContainer LOCALSTACK_CONTAINER = new LocalStackContainer(LOCALSTACK_IMAGE_NAME)
            .withServices(DYNAMODB);

    @Test
    void contextLoads() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(LOCALSTACK_CONTAINER.getAccessKey(), LOCALSTACK_CONTAINER.getSecretKey());
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(LOCALSTACK_CONTAINER.getEndpointOverride(DYNAMODB))
                .credentialsProvider(credentialsProvider)
                .build();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        DynamoDbTable<ExampleDocument> exampleTable = enhancedClient.table("ExampleTable", TableSchema.fromBean(ExampleDocument.class));
        exampleTable.createTable();

        String id = "c708f8f8-e9ef-4252-a7a6-5ed1ad4de620";
        String name = "Adrian";
        exampleTable.putItem(new ExampleDocument(id, name));

        ExampleDocument item = exampleTable.getItem(Key.builder().partitionValue(id).build());
        assertThat(item.getExampleId()).isEqualTo(id);
        assertThat(item.getExampleName()).isEqualTo(name);
    }

    @DynamoDbBean
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExampleDocument {
        private String exampleId;
        private String exampleName;

        @DynamoDbPartitionKey
        public String getExampleId() {
            return exampleId;
        }
    }

}
