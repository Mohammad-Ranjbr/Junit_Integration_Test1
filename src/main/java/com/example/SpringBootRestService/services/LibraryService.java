package com.example.SpringBootRestService.services;

public interface LibraryService {

    String buildID(String isbn , int aisle);
    boolean checkBookAlreadyExist(String book_id);

}
