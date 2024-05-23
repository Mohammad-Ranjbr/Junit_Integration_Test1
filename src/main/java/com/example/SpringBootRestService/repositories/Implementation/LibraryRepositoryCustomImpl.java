package com.example.SpringBootRestService.repositories.Implementation;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import com.example.SpringBootRestService.repositories.LibraryRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryRepositoryCustomImpl implements LibraryRepositoryCustom {


    private final LibraryRepository libraryRepository;
//
//    To resolve the cyclic dependency issue in your Spring Boot application, you can consider the following approaches:
//    Refactor the Code:
//    Break the Cycle: Identify the point in the cycle where the dependency can be refactored to break the cycle. This often involves redesigning the dependencies so that they no longer form a loop.
//    Use Constructor Injection: Prefer constructor injection over field injection. This makes it easier to spot cyclic dependencies early, as the cycle will be detected at the time of object creation.
//    Decouple Components: Ensure that your components are not tightly coupled. This can be achieved by using interfaces and separating concerns.
//    Use @Lazy Annotation:
//    Lazy Initialization: Apply the @Lazy annotation to one of the beans in the cycle. This will delay the initialization of the bean until it is first needed, thus breaking the cycle.
//    Consider @DependsOn:
//    Explicit Dependency: Use the @DependsOn annotation to specify the initialization order of the beans.

    @Autowired
    public LibraryRepositoryCustomImpl(@Lazy LibraryRepository libraryRepository){
        this.libraryRepository = libraryRepository;
    }
    @Override
    public List<Library> findAllByAuthor(String authorName) {
        List<Library> books = libraryRepository.findAll();
        List<Library> booksWithAuthor = new ArrayList<>();
        for(Library item : books){
            if(item.getAuthor().equalsIgnoreCase(authorName)){
                booksWithAuthor.add(item);
            }
        }
        return booksWithAuthor;
    }

}
