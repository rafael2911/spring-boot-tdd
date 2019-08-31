package br.com.crcarvalho.catalogo.repository;

import java.util.Optional;

import br.com.crcarvalho.catalogo.model.Pessoa;

public interface PessoaRepository {

	Pessoa save(Pessoa pessoa);

	Optional<Pessoa> findByCpf(String cpf);

	Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);

}
