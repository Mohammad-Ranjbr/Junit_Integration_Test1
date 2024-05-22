package com.example.SpringBootRestService.repositories;

import com.example.SpringBootRestService.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library,String> {



}
