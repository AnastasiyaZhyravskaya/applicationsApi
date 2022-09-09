package ru.ann.mast.applicationsApi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;

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
import ru.ann.mast.applicationsApi.controllers.ApplicationStatusController;
import ru.ann.mast.applicationsApi.entity.ApplicationStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationStatusIntegrationTest {
	@LocalServerPort
	private int port;
	
	@Autowired
	ApplicationStatusController applicationStatusController;
	
	
	@BeforeEach 
	public void setUp() throws Exception {
	    RestAssured.port = port;
	    
	}

	@AfterEach
	public void resetDb() {
		applicationStatusController.deleteAllStatus();
		applicationStatusController.resetAutoIncrement();
	}
	


	@Test
	public void addNewStatus_success() {

		ApplicationStatus testStatus = ApplicationStatus.builder()
															.status("TEST")
															.build();
												
		ApplicationStatus retrievedStatus = 
		given().contentType("application/json")
				.body(testStatus)
		
		.when().post("/status")
		
		.then().statusCode(HttpStatus.OK.value()).and()
		.extract().as(ApplicationStatus.class);
		assertThat(testStatus).usingRecursiveComparison()
        						.isEqualTo(retrievedStatus);
	}
	
	
	@Test
	public void addNewStatus_nullName_badRequest() {

		ApplicationStatus testStatus = ApplicationStatus.builder().build();
												
		given().contentType("application/json")
				.body(testStatus)
		
		.when().post("/status")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
		.body("info", equalTo("Method Argument Not Valid")).and()
		.body("errors", hasItem("Enter status name"));
	}
	
	
	@Test
	public void addNewStatus_whitespaceName_badRequest() {

		ApplicationStatus testStatus = ApplicationStatus.builder()
															.status("")
															.build();
												
		given().contentType("application/json")
				.body(testStatus)
		
		.when().post("/status")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
		.body("info", equalTo("Method Argument Not Valid")).and()
		.body("errors", hasItem("Enter status name"));
	}
	
	
	@Test
	public void addNewStatus_dubleStatus_badRequest() {
		createTestApplicationStatus("TEST_1");
		ApplicationStatus testStatus = ApplicationStatus.builder()
															.status("TEST_1")
															.build();
												
		given().contentType("application/json")
				.body(testStatus)
		
		.when().post("/status")
		
		.then()	.log().body() 
		.statusCode(HttpStatus.NOT_FOUND.value()).and()
		.body("info", equalTo("Status TEST_1 already exists"));
	}
	
	
	@Test
	public void deleteStatus_success() {

		String deleteStatus = createTestApplicationStatus("Status_Delete").getStatus();
		
		given().log().body().contentType("application/json")
				.pathParam("id", deleteStatus)
				
		.when().delete("/status/{id}")
		
		.then().log().body().
		statusCode(HttpStatus.OK.value()).and()
				.body(equalTo("Status "+deleteStatus+" was deleted"));
	}
	
	@Test
	public void deleteStatus_notStatus_badRequest() {
		
		given().contentType("application/json")
				.pathParam("id", 0)
				
		.when().delete("/status/{id}")
		
		.then().log().body().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Status 0 is not found"));
	}
	


	@Test
	public void getAllStatus_success() {
		ApplicationStatus status_1 = createTestApplicationStatus("STATUS_1");
		ApplicationStatus status_2 = createTestApplicationStatus("STATUS_2");
		ApplicationStatus status_3 = createTestApplicationStatus("STATUS_3");

		given().contentType("application/json")

		.when().get("/status")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("size()", is(3)).and()
				.body("[0].status", equalTo(status_1.getStatus())).and()
				.body("[1].status", equalTo(status_2.getStatus())).and()
				.body("[2].status", equalTo(status_3.getStatus()));
	}
	
	@Test
	public void getAllStatus_noStatus_success() {
		
		given().contentType("application/json")

		.when().get("/status")
		
		.then().statusCode(HttpStatus.OK.value());
		
	}

	private ApplicationStatus createTestApplicationStatus(String name) {
		ApplicationStatus testStatus= ApplicationStatus.builder()
														.status(name)
														.build();
		return applicationStatusController.addNewStatus(testStatus);
	}
	
	
}
