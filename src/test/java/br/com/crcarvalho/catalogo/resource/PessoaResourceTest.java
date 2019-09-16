package br.com.crcarvalho.catalogo.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import br.com.crcarvalho.catalogo.SpringBootTddApplicationTests;
import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.model.Telefone;
import io.restassured.http.ContentType;

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
	
	@Test
	public void deveSalvarNovaPessoaNoSistema() {
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome("Igor");
		pessoa.setCpf("65124213884");
		
		final Telefone telefone = new Telefone();
		telefone.setDdd("51");
		telefone.setNumero("37030614");
		
		pessoa.setTelefones(Arrays.asList(telefone));
		
		given()
			.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(pessoa)
		.when()
		.post("/pessoas")
		.then()
				.log().headers()
			.and()
				.log().body()
			.and()
				.statusCode(HttpStatus.CREATED.value())
				.header("Location", equalTo("http://localhost:" + port + "/pessoas/51/37030614"))
				.body("id", equalTo(6),
						"nome", equalTo("Igor"),
						"cpf", equalTo("65124213884"));
				
		
	}
	
}
