package com.example.SpringBootRestService;

import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

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

		//Save New Record
		Library library = new Library();
		library.setId("Book-2");
		library.setIsbn("Book");
		library.setAisle(2);
		library.setAuthor("Mohammad Ranjbar");
		library.setName("Spring Security");
		//libraryRepository.save(library);

		//Get Record
		Library library1 = libraryRepository.findById("Book-1").get();
		System.out.println(library.getId() + "	:	" + library1.getName());

		//Get All Records
		List<Library> allRecords = libraryRepository.findAll();
		for(Library item : allRecords){
			System.out.println(item.getId() + "	: " + item.getName());
		}

		//Delete Record
		//libraryRepository.delete(library);

	}

}
