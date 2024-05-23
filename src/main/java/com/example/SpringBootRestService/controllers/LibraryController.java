package com.example.SpringBootRestService.controllers;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.payloads.AddResponse;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {

    private final AddResponse addResponse;
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryController(LibraryRepository libraryRepository,AddResponse addResponse){
        this.addResponse = addResponse;
        this.libraryRepository = libraryRepository;
    }

    @PostMapping("/addBook")
    public ResponseEntity<AddResponse> addBook(@RequestBody Library library){
        HttpHeaders httpHeaders = new HttpHeaders();
        String id = library.getIsbn() + "-" + library.getAisle();
        library.setId(id);
        libraryRepository.save(library);
        addResponse.setId(id);
        addResponse.setMessage("Book Added Successfully");
        httpHeaders.add("Unique",id);
        return new ResponseEntity<>(addResponse,httpHeaders,HttpStatus.CREATED);
    }

}
