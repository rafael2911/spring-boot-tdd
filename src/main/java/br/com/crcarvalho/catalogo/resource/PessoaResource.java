package br.com.crcarvalho.catalogo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.model.Telefone;
import br.com.crcarvalho.catalogo.service.PessoaService;

@RestController
@RequestMapping("pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping("{ddd}/{numero}")
	public ResponseEntity<Pessoa> buscarPorDddENumeroDoTelefone(@PathVariable("ddd") String ddd,
			@PathVariable("numero") String numero){
		
		final Telefone telefone = new Telefone();
		telefone.setDdd(ddd);
		telefone.setNumero(numero);
		Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);
		
		return ResponseEntity.ok().body(pessoa);
	}
	
}
