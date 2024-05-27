package com.example.SpringBootRestService.services.Implementation;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import com.example.SpringBootRestService.services.LibraryService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository){
        this.libraryRepository = libraryRepository;
    }

    @Override
    public String buildID(String isbn, int aisle) {
        if(isbn.startsWith("Z"))
            return "OLD-" + isbn + "-" + aisle;
        return isbn + "-" + aisle;
    }

    public boolean checkBookAlreadyExist(String book_id){
        return libraryRepository.existsById(book_id);
    }

    @Override
    public Library getBookById(String id) {
        if(libraryRepository.findById(id).isPresent()){
            return libraryRepository.findById(id).get();
        }
        return null;
    }

}
