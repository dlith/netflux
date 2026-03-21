package com.vinsguru.recommendation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@EnableTestBinder
@AutoConfigureRestTestClient
@Sql(value = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class RecommendationServiceApiTest {

    @Autowired
    private RestTestClient testClient;

    @Autowired
    private InputDestination inputDestination;

    @Test
    public void recommendations() {

    }

    @Test
    public void recommendationsStream() {

    }

}
