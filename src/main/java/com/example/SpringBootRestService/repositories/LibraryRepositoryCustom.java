package com.example.SpringBootRestService.repositories;

import com.example.SpringBootRestService.models.Library;

import java.util.List;

public interface LibraryRepositoryCustom {

    List <Library> findAllByAuthor(String authorName);

}
