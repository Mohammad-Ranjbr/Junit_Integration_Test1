package com.example.SpringBootRestService;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class SpringBootRestServiceIT { // this is a convention for set name to integration test class with maven class name should end with IT
                                        // in unit test in default should end with Tests

    //In unit test you mock external dependencies
    //In integration test you mock database
    //Integration test => test connection between back-end and database
    //In integration test you call real endpoint because you have start server
    //TestRestTemplate is default library spring boot gives all for integration test

    @Test
    public void getBooksWithAuthorName() throws JSONException {
        String expected = "[\n" +
                "    {\n" +
                "        \"id\": \"Book-20\",\n" +
                "        \"aisle\": 20,\n" +
                "        \"isbn\": \"Book\",\n" +
                "        \"author\": \"Mohammad\",\n" +
                "        \"name\": \"Spring Boot\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"Book-21\",\n" +
                "        \"aisle\": 21,\n" +
                "        \"isbn\": \"Book\",\n" +
                "        \"author\": \"Mohammad\",\n" +
                "        \"name\": \"Spring Security\"\n" +
                "    }\n" +
                "]";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("http://localhost:8080/api/v1/getBooks/author/Mohammad",String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        JSONAssert.assertEquals(expected,responseEntity.getBody(),false);
    }

}
