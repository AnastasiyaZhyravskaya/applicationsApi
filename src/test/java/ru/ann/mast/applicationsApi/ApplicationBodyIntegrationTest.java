package ru.ann.mast.applicationsApi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.UUID;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import ru.ann.mast.applicationsApi.controllers.ApplicationBodyController;
import ru.ann.mast.applicationsApi.entity.ApplicationBody;;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationBodyIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	ApplicationBodyController applicationBodyController;
	
	
	@BeforeEach 
	public void setUp() throws Exception {
	    RestAssured.port = port;
	    
	}

	@AfterEach
	public void resetDb() {
		applicationBodyController.deleteAllBody();
		applicationBodyController.resetAutoIncrement();
	}
	
	
	@Test
	public void addNewBody_success() {

		ApplicationBody testBody = ApplicationBody.builder()
				.applicationBody(UUID.randomUUID().toString()).build();
												
		ApplicationBody retrievedBody = 
		given().contentType("application/json")
				.body(testBody)
		
		.when().post("/bodys")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1)).and()
		.extract().as(ApplicationBody.class);
		assertThat(testBody).usingRecursiveComparison()
								.ignoringFields("id")
        						.isEqualTo(retrievedBody);
	}
	
	@Test
	public void addNewBody_noBody_success() {

		ApplicationBody testBody = ApplicationBody.builder().build();
												
		ApplicationBody retrievedBody = 
		given().contentType("application/json")
				.body(testBody)
		
		.when().post("/bodys")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1)).and()
		.extract().as(ApplicationBody.class);
		assertThat(testBody).usingRecursiveComparison()
								.ignoringFields("id")
        						.isEqualTo(retrievedBody);
	}

	
	
	@Test
	public void getBody_success() {
		ApplicationBody testBody = createTestBody();
		
		ApplicationBody retrievedBody = 
		given().contentType("application/json")
				.pathParam("id", testBody.getId())
		
		.when().get("/bodys/{id}")
		
		.then().statusCode(HttpStatus.OK.value())
				.extract().as(ApplicationBody.class);
		
		assertThat(testBody).usingRecursiveComparison()
				.isEqualTo(retrievedBody);
	}
			
	@Test
	public void getBody_noIdBody_badRequest() {

		given().contentType("application/json")
				.pathParam("id", 0)
		
		.when().get("/bodys/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Application body is not found, id=0"));
	}
	
	@Test
	public void getBodys_stringIdBodys_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().get("/bodys/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));

	}
	
	
	
	@Test
	public void deleteBody_success() {

		int id = createTestBody().getId();
		
		given().contentType("application/json")
				.pathParam("id", id)
				
		.when().delete("/bodys/{id}")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body(equalTo("Application body with id="+id+" was deleted"));
	}
	
	@Test
	public void deleteBodys_notId_badRequest() {
		
		given().contentType("application/json")
				.pathParam("id", 0)
				
		.when().delete("/bodys/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Application body is not found, id=0"));
	}
	
	@Test
	public void deleteBodys_stringIdBodys_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().delete("/bodys/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));
	}
	

	private ApplicationBody createTestBody() {
		ApplicationBody testBody = ApplicationBody.builder()
				.applicationBody(UUID.randomUUID().toString())
				.build();
		applicationBodyController.addApplicationBody(testBody);
		return testBody;
	}
	
	
}
