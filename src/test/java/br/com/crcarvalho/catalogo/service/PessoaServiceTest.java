package br.com.crcarvalho.catalogo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.model.Telefone;
import br.com.crcarvalho.catalogo.repository.PessoaRepository;
import br.com.crcarvalho.catalogo.service.exception.CpfDuplicadoException;
import br.com.crcarvalho.catalogo.service.exception.TelefoneDuplicadoException;
import br.com.crcarvalho.catalogo.service.exception.TelefoneNaoEncontradoException;
import br.com.crcarvalho.catalogo.service.impl.PessoaServiceImpl;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {
	
	private static final String NUMERO = "988818881";
	private static final String DDD = "73";
	private static final String CPF = "11111111111";
	private static final String NOME = "Rafael";

	@MockBean
	private PessoaRepository pessoaRepository;
	
	private PessoaService pessoaService;
	
	private Pessoa pessoa;
	private Telefone telefone;
	
	@Before
	public void setUp() {
		
		pessoaService = new PessoaServiceImpl(pessoaRepository);
		
		telefone = new Telefone();
		telefone.setDdd(DDD);
		telefone.setNumero(NUMERO);
		
		pessoa = new Pessoa();
		pessoa.setNome(NOME);
		pessoa.setCpf(CPF);
		pessoa.setTelefones(new ArrayList<Telefone>(java.util.Arrays.asList(telefone)));
		
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
		
	}
	
	@Test
	public void deveSalvarPessoaNoRepositorio() {
		pessoaService.salvar(pessoa);
		
		// verifica se o metodo save, de pessoaRepository, foi chamado 
		verify(pessoaRepository).save(pessoa);
	}
	
	@Test(expected = CpfDuplicadoException.class)
	public void naoDeveSalvarDuasPessoasComOMesmoCpf() {
		// quando chamar o metodo findByCpf, retornar o optional com a pessoa jah cadastrada
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
		
		// deverá lancar uma exception pois a pessoa jah esta cadastrada
		pessoaService.salvar(pessoa);
	}
	
	@Test(expected = TelefoneDuplicadoException.class)
	public void naoDeveSalvarDuasPessoasComOMesmoTelefone() {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
		
		pessoaService.salvar(pessoa);
	}
	
	@Test(expected = TelefoneNaoEncontradoException.class)
	public void deveLancarTelefoneNaoEncontradoExceptionQuandoNaoExistirPessoaComODddENumero() {
		pessoaService.buscarPorTelefone(telefone);
	}
	
	@Test
	public void deveProcurarPessoaPeloDddENumeroDoTelefone() {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
		
		Pessoa pessoaTeste = pessoaService.buscarPorTelefone(telefone);
		
		verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);
		
		assertThat(pessoaTeste).isNotNull();
		assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
		assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
	}
	
}
