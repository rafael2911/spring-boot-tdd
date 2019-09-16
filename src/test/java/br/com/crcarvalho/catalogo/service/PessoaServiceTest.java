package br.com.crcarvalho.catalogo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
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
		pessoa.setTelefones(Arrays.asList(telefone));
		
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
		when(pessoaRepository.findByTelefonesDddAndTelefonesNumero(DDD, NUMERO)).thenReturn(Optional.empty());
		
	}
	
	@Test
	public void deveSalvarPessoaNoRepositorio() {
		pessoaService.salvar(pessoa);
		
		// verifica se o metodo save, de pessoaRepository, foi chamado 
		verify(pessoaRepository).save(pessoa);
	}
	
	@Test
	public void naoDeveSalvarDuasPessoasComOMesmoCpf() {
		// quando chamar o metodo findByCpf, retornar o optional com a pessoa jah cadastrada
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
		
		expectedException.expect(CpfDuplicadoException.class);
		expectedException.expectMessage("Já existe pessoa cadastrada com o CPF " + pessoa.getCpf());
		
		// deverá lancar uma exception pois a pessoa jah esta cadastrada
		pessoaService.salvar(pessoa);
	}
	
	@Test
	public void naoDeveSalvarDuasPessoasComOMesmoTelefone() {
		when(pessoaRepository.findByTelefonesDddAndTelefonesNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
		
		expectedException.expect(TelefoneDuplicadoException.class);
		expectedException.expectMessage("Já existe pessoa cadastrada com o telefone (" + DDD + ") " + NUMERO);
		
		pessoaService.salvar(pessoa);
	}
	
	@Test(expected = TelefoneNaoEncontradoException.class)
	public void deveLancarTelefoneNaoEncontradoExceptionQuandoNaoExistirPessoaComODddENumero() {
		pessoaService.buscarPorTelefone(telefone);
	}
	
	@Test
	public void deveRetornarDadosDoTelefoneDentroDaExecucaoDeTelefoneNaoEncontradoException() {
		expectedException.expect(TelefoneNaoEncontradoException.class);
		expectedException.expectMessage("Não existe pessoa com o telefone (" + DDD +") " + NUMERO);
		
		pessoaService.buscarPorTelefone(telefone);
	}
	
	@Test
	public void deveProcurarPessoaPeloDddENumeroDoTelefone() {
		when(pessoaRepository.findByTelefonesDddAndTelefonesNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
		
		Pessoa pessoaTeste = pessoaService.buscarPorTelefone(telefone);
		
		verify(pessoaRepository).findByTelefonesDddAndTelefonesNumero(DDD, NUMERO);
		
		assertThat(pessoaTeste).isNotNull();
		assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
		assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
	}
	
}
