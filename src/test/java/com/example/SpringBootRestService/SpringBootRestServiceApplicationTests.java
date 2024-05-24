package com.example.SpringBootRestService;

import com.example.SpringBootRestService.services.Implementation.LibraryServiceImpl;
import com.example.SpringBootRestService.services.LibraryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootRestServiceApplicationTests {

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

}
