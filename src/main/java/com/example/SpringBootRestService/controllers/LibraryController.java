package com.example.SpringBootRestService.controllers;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.payloads.AddResponse;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import com.example.SpringBootRestService.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {

    private final LibraryService libraryService;
    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryController(LibraryRepository libraryRepository,LibraryService libraryService){
        this.libraryService = libraryService;
        this.libraryRepository = libraryRepository;
    }

    @PostMapping("/addBook")
    public ResponseEntity<AddResponse> addBook(@RequestBody Library library){
        String id = libraryService.buildID(library.getIsbn(),library.getAisle());
        if(!libraryService.checkBookAlreadyExist(id)){
            HttpHeaders httpHeaders = new HttpHeaders();
            library.setId(id);
            libraryRepository.save(library);
            httpHeaders.add("Unique",id);
            return new ResponseEntity<>(new AddResponse(id,"Book Added Successfully"),httpHeaders,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new AddResponse(id,"Book Already Exist"),HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/getBooks/{id}")
    public ResponseEntity<Library> getBooks(@PathVariable("id") String book_id){
        if(libraryRepository.findById(book_id).isPresent()){
            return new ResponseEntity<>(libraryRepository.findById(book_id).get(),HttpStatus.OK);
        } else { // SEARCH
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getBooks/author/{authorName}")
    public ResponseEntity<List<Library>> getBooksWithAuthorName(@PathVariable("authorName") String author_name){
        //return new ResponseEntity<>(libraryRepository.findByAuthor(author_name),HttpStatus.OK);
        return new ResponseEntity<>(libraryRepository.findAllByAuthor(author_name),HttpStatus.OK);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Library> updateBook(@PathVariable("id") String book_id , @RequestBody Library library){
        if(libraryRepository.findById(book_id).isPresent()) {
            Library libraryInDB = libraryRepository.findById(book_id).get();
            libraryInDB.setAisle(library.getAisle());
            libraryInDB.setName(library.getName());
            libraryInDB.setAuthor(library.getAuthor());
            libraryRepository.save(libraryInDB);
            return new ResponseEntity<>(libraryInDB, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
