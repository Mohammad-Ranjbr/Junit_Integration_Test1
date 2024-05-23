package com.example.SpringBootRestService.controllers;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {

    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryController(LibraryRepository libraryRepository){
        this.libraryRepository = libraryRepository;
    }

    @PostMapping("/addBook")
    public ResponseEntity<Library> addBook(@RequestBody Library library){
        library.setId(library.getIsbn() + "-" + library.getAisle());
        libraryRepository.save(library);
        return new ResponseEntity<>(library, HttpStatus.CREATED);
    }

}
