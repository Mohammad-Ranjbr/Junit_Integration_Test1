package com.example.SpringBootRestService.repositories;

import com.example.SpringBootRestService.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library,String> {

    List<Library> findByAuthor(String authorName);

}
