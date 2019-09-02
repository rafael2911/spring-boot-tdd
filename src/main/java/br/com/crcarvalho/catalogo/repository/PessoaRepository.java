package br.com.crcarvalho.catalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.repository.helper.PessoaRepositoryQueries;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQueries {

	Optional<Pessoa> findByCpf(String cpf);

	Optional<Pessoa> findByTelefonesDddAndTelefonesNumero(String ddd, String numero);

}
