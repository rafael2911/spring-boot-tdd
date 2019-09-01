package br.com.crcarvalho.catalogo.service;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.model.Telefone;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa);

	Pessoa buscarPorTelefone(Telefone telefone);

}
