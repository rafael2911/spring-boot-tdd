package br.com.crcarvalho.catalogo.repository.helper;

import java.util.List;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.repository.filtro.PessoaFiltro;

public interface PessoaRepositoryQueries {
	
	List<Pessoa> filtrar(PessoaFiltro filtro);
	
}
