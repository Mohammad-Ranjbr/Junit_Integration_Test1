package com.example.SpringBootRestService;

import com.example.SpringBootRestService.controllers.LibraryController;
import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.payloads.AddResponse;
import com.example.SpringBootRestService.services.Implementation.LibraryServiceImpl;
import com.example.SpringBootRestService.services.LibraryService;
import org.aspectj.runtime.internal.Conversions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringBootRestServiceApplicationTests {

	private final LibraryController libraryController;
	//Fake Object -> @Mock    -    Fake Bean -> @MockBean
	@MockBean
	private LibraryService libraryService;

	@Autowired
	public SpringBootRestServiceApplicationTests(LibraryController libraryController){
		this.libraryController = libraryController;
	}
	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogin(){
		LibraryService libraryService = new LibraryServiceImpl(null);
		String id1 = libraryService.buildID("ZMAN",10);
		Assertions.assertEquals(id1,"OLD-ZMAN-10");
		String id2 = libraryService.buildID("Book",11);
		Assertions.assertEquals(id2,"Book-11");
	}

	@Test
	//Approach 1 : Call method in controller class and pass them object
	public void addBook_whenBookDoesNotExist_shouldReturnCreated(){
		Library library = new Library();
		library.setAisle(20);
		library.setName("Spring");
		library.setIsbn("Book");
		library.setAuthor("Mohammad Ranjbar");
		library.setId("Book-20");
		when(libraryService.buildID(library.getIsbn(),library.getAisle())).thenReturn(library.getId());
		when(libraryService.checkBookAlreadyExist(library.getId())).thenReturn(false);
		ResponseEntity<AddResponse> responseEntity = libraryController.addBook(library);
		System.out.println(responseEntity.getStatusCode());
		Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		// Body Of ResponseEntity
		Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(library.getId(),responseEntity.getBody().getId());
		Assertions.assertEquals("Book Added Successfully",responseEntity.getBody().getMessage());
		//This code checks whether the buildID method of the LibraryService class with the specified
		//parameters (library.getIsbn() and library.getAisle()) is called exactly once. If this call does not occur
		//exactly once or any call is missing, the test will fail.
		verify(libraryService, times(1)).buildID(library.getIsbn(), library.getAisle());
		verify(libraryService, times(1)).checkBookAlreadyExist(library.getId());
	}

	@Test
	public void addBook_whenBookAlreadyExists_shouldReturnAccepted(){
		Library library = new Library();
		library.setAisle(20);
		library.setName("Spring");
		library.setIsbn("Book");
		library.setAuthor("Mohammad Ranjbar");
		library.setId("Book-20");
		when(libraryService.buildID(anyString(), Conversions.intValue(any()))).thenReturn(library.getId());
		when(libraryService.checkBookAlreadyExist(anyString())).thenReturn(true);
		ResponseEntity<AddResponse> responseEntity = libraryController.addBook(library);
		System.out.println(responseEntity.getStatusCode());
		Assertions.assertEquals(HttpStatus.ACCEPTED,responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertEquals(library.getId(),responseEntity.getBody().getId());
		Assertions.assertEquals("Book Already Exist",responseEntity.getBody().getMessage());
	}

}
