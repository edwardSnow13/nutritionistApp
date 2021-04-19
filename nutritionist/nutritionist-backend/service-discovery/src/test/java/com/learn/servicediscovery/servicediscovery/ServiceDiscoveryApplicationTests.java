package com.learn.servicediscovery.servicediscovery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceDiscoveryApplicationTests {
    @LocalServerPort
    private int port;

    @Test
    void checkEurekaServer() throws Exception {
        TestRestTemplate restTemplate = new TestRestTemplate();
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://127.0.0.1:"+port+"/", String.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertTrue(responseEntity.getBody().contains("Instances currently registered with Eureka"));

    }

}