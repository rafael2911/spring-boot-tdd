package br.com.crcarvalho.catalogo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.crcarvalho.catalogo.model.Erro;
import br.com.crcarvalho.catalogo.service.exception.CpfDuplicadoException;
import br.com.crcarvalho.catalogo.service.exception.TelefoneDuplicadoException;
import br.com.crcarvalho.catalogo.service.exception.TelefoneNaoEncontradoException;

@ControllerAdvice
public class ExceptionHandlerConfig {
	
	@ExceptionHandler(TelefoneNaoEncontradoException.class)
	public ResponseEntity<Erro> telefoneNaoEncontrado(TelefoneNaoEncontradoException ex) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(ex.getMessage()));
	}
	
	@ExceptionHandler(CpfDuplicadoException.class)
	public ResponseEntity<Erro> cpfDuplicado(CpfDuplicadoException ex){
		
		return ResponseEntity.badRequest().body(new Erro(ex.getMessage()));
	}
	
	@ExceptionHandler(TelefoneDuplicadoException.class)
	public ResponseEntity<Erro> telefoneDuplicado(TelefoneDuplicadoException ex){
		
		return ResponseEntity.badRequest().body(new Erro(ex.getMessage()));
	}
	
}
