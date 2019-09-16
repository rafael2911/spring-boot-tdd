package br.com.crcarvalho.catalogo.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.model.Telefone;
import br.com.crcarvalho.catalogo.repository.PessoaRepository;
import br.com.crcarvalho.catalogo.repository.filtro.PessoaFiltro;
import br.com.crcarvalho.catalogo.service.PessoaService;

@RestController
@RequestMapping("pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping("{ddd}/{numero}")
	public ResponseEntity<Pessoa> buscarPorDddENumeroDoTelefone(@PathVariable("ddd") String ddd,
			@PathVariable("numero") String numero){
		
		final Telefone telefone = new Telefone();
		telefone.setDdd(ddd);
		telefone.setNumero(numero);
		Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);
		
		return ResponseEntity.ok().body(pessoa);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@RequestBody Pessoa pessoa, HttpServletResponse response){
		pessoaService.salvar(pessoa);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{ddd}/{numero}")
			.buildAndExpand(
					pessoa.getTelefones().get(0).getDdd(),
					pessoa.getTelefones().get(0).getNumero()).toUri();
		
		response.setHeader("Location", location.toASCIIString());
		
		return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
	}
	
	@PostMapping("filtrar")
	public List<Pessoa> filtrar(@RequestBody PessoaFiltro filtro){
		
		return pessoaRepository.filtrar(filtro);
	}
	
}
