package br.com.crcarvalho.catalogo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.crcarvalho.catalogo.model.Erro;
import br.com.crcarvalho.catalogo.service.exception.TelefoneNaoEncontradoException;

@ControllerAdvice
public class ExceptionHandlerConfig {
	
	@ExceptionHandler(TelefoneNaoEncontradoException.class)
	public ResponseEntity<Erro> telefoneNaoEncontrado(TelefoneNaoEncontradoException ex) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(ex.getMessage()));
	}
	
}
