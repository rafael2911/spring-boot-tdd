package br.com.crcarvalho.catalogo.service.exception;

public class TelefoneNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TelefoneNaoEncontradoException(String mensagem) {
		super(mensagem);

	}

}
