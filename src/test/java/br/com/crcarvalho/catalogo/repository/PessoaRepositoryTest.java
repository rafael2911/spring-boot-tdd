package br.com.crcarvalho.catalogo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.repository.filtro.PessoaFiltro;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Test
	public void deveProcurarPessoaPeloCpf() {

		Optional<Pessoa> optional = pessoaRepository.findByCpf("38767897100");

		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();

		assertThat(pessoa.getId()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");

	}

	@Test
	public void naoDeveEncontrarPessoaDeCpfInexistente() {
		Optional<Pessoa> optional = pessoaRepository.findByCpf("38767897200");

		assertThat(optional.isPresent()).isFalse();
	}
	
	@Test
	public void deveEncontrarPessoaPeloDddENumeroDeTelefone() {
		Optional<Pessoa> optional = pessoaRepository.findByTelefonesDddAndTelefonesNumero("86", "35006330");
		
		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();

		assertThat(pessoa.getId()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");
	}
	
	@Test
	public void naoDeveEncontrarPessoaPeloDddETelefoneInexistente() {
		Optional<Pessoa> optional = pessoaRepository.findByTelefonesDddAndTelefonesNumero("11", "32812020");
		
		assertThat(optional.isPresent()).isFalse();
	}
	
	@Test
	public void deveFiltrarPessoasPorParteDoNome() {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setNome("a");
		
		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);
		
		assertThat(pessoas.size()).isEqualTo(3);
	}
	
	@Test
	public void deveFiltrarPessoasPorParteDoCpf() {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setCpf("78");
		
		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);
		
		assertThat(pessoas.size()).isEqualTo(3);
	}
	
	@Test
	public void deveFiltrarPessoasPorFiltroCompostoDeCpfENome() {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setCpf("78");
		filtro.setNome("a");
		
		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);
		
		assertThat(pessoas.size()).isEqualTo(2);
	}
	
	@Test
	public void deveFiltrarPessoasPeloDddDoTelefone() {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setDdd("21");
		
		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);
		
		assertThat(pessoas.size()).isEqualTo(1);
	}
	
	@Test
	public void deveFiltrarPessoasPeloNumeroDoTelefone() {
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setTelefone("32810000");
		
		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);
		
		assertThat(pessoas.size()).isEqualTo(0);
	}

}
