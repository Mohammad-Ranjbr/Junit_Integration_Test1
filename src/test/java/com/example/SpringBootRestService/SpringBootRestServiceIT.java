package com.example.SpringBootRestService;

import com.example.SpringBootRestService.models.Library;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

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
        //Text Block
        String expected = """ 
                [
                    {
                        "id": "Book-20",
                        "aisle": 20,
                        "isbn": "Book",
                        "author": "Mohammad",
                        "name": "Spring Boot"
                    },
                    {
                        "id": "Book-21",
                        "aisle": 21,
                        "isbn": "Book",
                        "author": "Mohammad",
                        "name": "Spring Security"
                    }
                ]""";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("http://localhost:8080/api/v1/getBooks/author/Mohammad",String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        JSONAssert.assertEquals(expected,responseEntity.getBody(),false);
    }

    @Test
    public void addBookIntegrationTest(){
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Library> request = new HttpEntity<>(buildlibrary(),httpHeaders);
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("http://localhost:8080/api/v1/addBook",request,String.class);
        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        Assertions.assertEquals(buildlibrary().getId(), Objects.requireNonNull(responseEntity.getHeaders().get("Unique")).get(0));
    }

    public Library buildlibrary(){
        Library library = new Library();
        library.setAisle(30);
        library.setName("Spring");
        library.setIsbn("Book");
        library.setAuthor("Mohammad Ranjbar");
        library.setId("Book-30");
        return library;
    }

}
