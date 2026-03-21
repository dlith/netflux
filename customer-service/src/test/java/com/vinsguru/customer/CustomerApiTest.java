package com.vinsguru.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
public class CustomerApiTest {

    @Autowired
    private RestTestClient testClient;

    @Test
    public void customerDetails() {
    }

    @Test
    public void customerNotFound() {
    }

}
