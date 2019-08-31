package br.com.crcarvalho.catalogo.service.impl;

import java.util.Optional;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.model.Telefone;
import br.com.crcarvalho.catalogo.repository.PessoaRepository;
import br.com.crcarvalho.catalogo.service.PessoaService;
import br.com.crcarvalho.catalogo.service.exception.CpfDuplicadoException;
import br.com.crcarvalho.catalogo.service.exception.TelefoneDuplicadoException;

public class PessoaServiceImpl implements PessoaService {

	private final PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());
		
		if(optional.isPresent())
			throw new CpfDuplicadoException();
		
		Telefone telefone = pessoa.getTelefones().get(0);
		optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		
		if(optional.isPresent())
			throw new TelefoneDuplicadoException();
		
		return pessoaRepository.save(pessoa);
	}

}
