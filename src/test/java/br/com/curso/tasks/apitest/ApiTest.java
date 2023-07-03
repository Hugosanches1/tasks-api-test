package br.com.curso.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefa() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2023-07-07\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2005-05-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
			;
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		Integer id = RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2023-07-07\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			.extract().path("id")
			;
		
		//remover
		RestAssured.given()
			.when()
				.delete("todo/"+id)
			.then()
				.statusCode(204)
			;
	}
	
}
