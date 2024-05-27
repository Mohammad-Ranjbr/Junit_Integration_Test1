package com.example.SpringBootRestService.services;

import com.example.SpringBootRestService.models.Library;

public interface LibraryService {

    String buildID(String isbn , int aisle);
    boolean checkBookAlreadyExist(String book_id);
    Library getBookById(String id);


}
