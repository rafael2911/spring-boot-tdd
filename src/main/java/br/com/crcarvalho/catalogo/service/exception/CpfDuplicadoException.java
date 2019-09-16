package br.com.crcarvalho.catalogo.service.exception;

public class CpfDuplicadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CpfDuplicadoException(String mensagem) {
		super(mensagem);
	}

}
