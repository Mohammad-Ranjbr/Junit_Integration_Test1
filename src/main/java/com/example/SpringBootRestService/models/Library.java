package com.example.SpringBootRestService.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "library")
public class Library {

    @Id
    private String id;
    private int aisle;
    private String isbn;
    private String author;
    @Column(name = "book_name")
    private String name;

}
