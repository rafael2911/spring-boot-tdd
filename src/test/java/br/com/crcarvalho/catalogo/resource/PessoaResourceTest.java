package br.com.crcarvalho.catalogo.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import br.com.crcarvalho.catalogo.SpringBootTddApplicationTests;

public class PessoaResourceTest extends SpringBootTddApplicationTests {
	
	@Test
	public void deveProcurarPessoaPeloDddENumeroDoTelefone() {
		given()
			.pathParam("ddd", "86")
			.pathParam("numero", "35006330")
		.get("/pessoas/{ddd}/{numero}")
		.then()
			.log().body()
			.and()
			.statusCode(HttpStatus.OK.value())
			.and()
			.body("id", equalTo(3),
					"nome", equalTo("Cauê"),
					"cpf", equalTo("38767897100"));
	}
	
	@Test
	public void deveRetornarErroNaoEncontradoQuandoBuscarPessoaPorTelefoneInexistente() {
		given()
			.pathParam("ddd", "99")
			.pathParam("numero", "35006330")
		.get("/pessoas/{ddd}/{numero}")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("erro", equalTo("Não existe pessoa com o telefone (99) 35006330"));
	}
	
}
