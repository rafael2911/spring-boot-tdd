package br.com.crcarvalho.catalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crcarvalho.catalogo.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Optional<Pessoa> findByCpf(String cpf);

	Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);

}
