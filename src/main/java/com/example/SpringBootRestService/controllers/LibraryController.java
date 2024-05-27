package com.example.SpringBootRestService.controllers;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.payloads.AddResponse;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import com.example.SpringBootRestService.services.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    public LibraryController(LibraryRepository libraryRepository,LibraryService libraryService){
        this.libraryService = libraryService;
        this.libraryRepository = libraryRepository;
    }

    @PostMapping("/addBook")
    public ResponseEntity<AddResponse> addBook(@RequestBody Library library){
        String id = libraryService.buildID(library.getIsbn(),library.getAisle());
        if(!libraryService.checkBookAlreadyExist(id)){
            logger.info("Book do not exist so creating one...");
            HttpHeaders httpHeaders = new HttpHeaders();
            library.setId(id);
            libraryRepository.save(library);
            httpHeaders.add("Unique",id);
            return new ResponseEntity<>(new AddResponse(id,"Book Added Successfully"),httpHeaders,HttpStatus.CREATED);
        } else {
            logger.info("Book exist so skipping creation...");
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
//        if(libraryRepository.findById(book_id).isPresent()) {
//            Library libraryInDB = libraryService.getBookById(book_id);
//            libraryInDB.setAisle(library.getAisle());
//            libraryInDB.setName(library.getName());
//            libraryInDB.setAuthor(library.getAuthor());
//            libraryRepository.save(libraryInDB);
//            return new ResponseEntity<>(libraryInDB, HttpStatus.OK);
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
        Library libraryInDB = libraryService.getBookById(book_id);
        libraryInDB.setAisle(library.getAisle());
        libraryInDB.setName(library.getName());
        libraryInDB.setAuthor(library.getAuthor());
        libraryRepository.save(libraryInDB);
        return new ResponseEntity<>(libraryInDB, HttpStatus.OK);
    }

//    @DeleteMapping("/deleteBook/{id}")
//    public ResponseEntity<String> deleteBook(@PathVariable("id") String book_id){
////        if(libraryRepository.findById(book_id).isPresent()) {
////            libraryRepository.deleteById(book_id);
////            logger.info("Book is deleted...");
////            return new ResponseEntity<>("Book Deleted Successfully",HttpStatus.OK);
////        } else {
////            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
////        }
//        Library library = libraryService.getBookById(book_id);
//        libraryRepository.delete(library);
//        logger.info("Book is deleted...");
//        return new ResponseEntity<>("Book Deleted Successfully",HttpStatus.OK);
//    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestBody Library library1){
//        if(libraryRepository.findById(book_id).isPresent()) {
//            libraryRepository.deleteById(book_id);
//            logger.info("Book is deleted...");
//            return new ResponseEntity<>("Book Deleted Successfully",HttpStatus.OK);
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
        Library library = libraryService.getBookById(library1.getId());
        libraryRepository.delete(library);
        logger.info("Book is deleted...");
        return new ResponseEntity<>("Book Deleted Successfully",HttpStatus.OK);
    }

}
