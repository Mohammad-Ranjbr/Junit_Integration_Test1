package com.example.SpringBootRestService;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRestServiceApplication implements CommandLineRunner {

	private final LibraryRepository libraryRepository;

	@Autowired
	public SpringBootRestServiceApplication(LibraryRepository libraryRepository){
		this.libraryRepository = libraryRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		Library library = new Library();
//		library.setId("Book-1");
//		library.setIsbn("Book");
//		library.setAisle(1);
//		library.setAuthor("Mohammad Ranjbar");
//		library.setName("SpringBoot Rest Service");
//		libraryRepository.save(library);

		Library library = libraryRepository.findById("Book-1").get();
		System.out.println(library.getId() + "	:	" + library.getName());

	}

}
