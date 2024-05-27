package com.example.SpringBootRestService;

import com.example.SpringBootRestService.controllers.LibraryController;
import com.example.SpringBootRestService.models.Library;
import com.example.SpringBootRestService.payloads.AddResponse;
import com.example.SpringBootRestService.repositories.LibraryRepository;
import com.example.SpringBootRestService.services.Implementation.LibraryServiceImpl;
import com.example.SpringBootRestService.services.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.runtime.internal.Conversions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootRestServiceApplicationTests {

	private final LibraryController libraryController;
	//Fake Object -> @Mock    -    Fake Bean -> @MockBean
	@MockBean
	private LibraryRepository libraryRepository;
	@MockBean
	private LibraryService libraryService;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	public SpringBootRestServiceApplicationTests(LibraryController libraryController){
		this.libraryController = libraryController;
	}
	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogin(){
		LibraryService libraryService = new LibraryServiceImpl(libraryRepository);
		String id1 = libraryService.buildID("ZMAN",10);
		Assertions.assertEquals(id1,"OLD-ZMAN-10");
		String id2 = libraryService.buildID("Book",11);
		Assertions.assertEquals(id2,"Book-11");
	}

	@Test
	//Approach 1 : Call method in controller class and pass them object
	public void addBook_whenBookDoesNotExist_shouldReturnCreated(){
		Library library = buildlibrary();
		when(libraryService.buildID(library.getIsbn(),library.getAisle())).thenReturn(library.getId());
		when(libraryService.checkBookAlreadyExist(library.getId())).thenReturn(false);
		when(libraryRepository.save(any())).thenReturn(library);
		ResponseEntity<AddResponse> responseEntity = libraryController.addBook(library); // Invoke Method
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
		Library library = buildlibrary();
		when(libraryService.buildID(anyString(), Conversions.intValue(any()))).thenReturn(library.getId());
		when(libraryService.checkBookAlreadyExist(anyString())).thenReturn(true);
		when(libraryRepository.save(any())).thenReturn(library);
		ResponseEntity<AddResponse> responseEntity = libraryController.addBook(library);
		System.out.println(responseEntity.getStatusCode());
		Assertions.assertEquals(HttpStatus.ACCEPTED,responseEntity.getStatusCode());
		//When you invoke method your response as Object not JSON
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertEquals(library.getId(),responseEntity.getBody().getId());
		Assertions.assertEquals("Book Already Exist",responseEntity.getBody().getMessage());
	}

	//Mock for invoke method - MockMvc for service call`s

	@Test
	public void addBook_whenBookDoesNotExist_shouldReturnCreated_controllerTest() throws Exception {
		Library library = buildlibrary();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonLibrary = objectMapper.writeValueAsString(library);
		when(libraryService.buildID(library.getIsbn(),library.getAisle())).thenReturn(library.getId());
		when(libraryService.checkBookAlreadyExist(library.getId())).thenReturn(false);
		when(libraryRepository.save(any())).thenReturn(library);
		//Perform method for call service - Mock Call
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/addBook")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLibrary)).andExpect(MockMvcResultMatchers.status().isCreated())
				//When you call service (Mock Call) your response as JSON not Object
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(library.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book Added Successfully"))
				.andDo(MockMvcResultHandlers.print());
		verify(libraryService,times(1)).buildID(library.getIsbn(),library.getAisle());
		verify(libraryService,times(1)).checkBookAlreadyExist(library.getId());
	}

//	@Test
//	public void getBook_withAuthorName_shouldReturnOk() throws Exception {
//		List<Library> libraryList = new ArrayList<>();
//		Library library1 = new Library();
//		library1.setAisle(20);
//		library1.setName("Spring");
//		library1.setIsbn("Book");
//		library1.setAuthor("Mohammad");
//		library1.setId("Book-20");
//		Library library2 = new Library();
//		library2.setAisle(20);
//		library2.setName("Spring Security");
//		library2.setIsbn("Book");
//		library2.setAuthor("Mohammad");
//		library2.setId("Book-21");
//		libraryList.add(library1);
//		libraryList.add(library2);
//		when(libraryRepository.findAllByAuthor(any())).thenReturn(libraryList);
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/getBooks/author/{authorName}").param("authorName","Mohammad"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value("Book-20"))
//				.andDo(MockMvcResultHandlers.print());
//	}

	@Test
	public void updateBook_shouldReturnOk() throws Exception {
		Library library = buildlibrary();
		ObjectMapper objectMapper = new ObjectMapper();
		String updatedLibraryJson = objectMapper.writeValueAsString(updatedlibrary());
		when(libraryService.getBookById(library.getId())).thenReturn(library);
		when(libraryRepository.save(any())).thenReturn(library);
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/updateBook/"+library.getId())
				.contentType(MediaType.APPLICATION_JSON).content(updatedLibraryJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("{\"id\":\"Book-20\",\"aisle\":21,\"isbn\":\"Book\",\"author\":\"Mohammad\",\"name\":\"Python\"}"))
				.andDo(MockMvcResultHandlers.print());
		verify(libraryService,times(1)).getBookById(library.getId());
	}

	public Library buildlibrary(){
		Library library = new Library();
		library.setAisle(20);
		library.setName("Spring");
		library.setIsbn("Book");
		library.setAuthor("Mohammad Ranjbar");
		library.setId("Book-20");
		return library;
	}

	public Library updatedlibrary(){
		Library library = new Library();
		library.setAisle(21);
		library.setName("Python");
		library.setIsbn("Book");
		library.setAuthor("Mohammad");
		library.setId("Book-21");
		return library;
	}

}
