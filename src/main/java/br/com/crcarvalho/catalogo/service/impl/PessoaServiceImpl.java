package br.com.crcarvalho.catalogo.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.model.Telefone;
import br.com.crcarvalho.catalogo.repository.PessoaRepository;
import br.com.crcarvalho.catalogo.service.PessoaService;
import br.com.crcarvalho.catalogo.service.exception.CpfDuplicadoException;
import br.com.crcarvalho.catalogo.service.exception.TelefoneDuplicadoException;
import br.com.crcarvalho.catalogo.service.exception.TelefoneNaoEncontradoException;

@Service
public class PessoaServiceImpl implements PessoaService {

	private final PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());
		
		if(optional.isPresent())
			throw new CpfDuplicadoException("Já existe pessoa cadastrada com o CPF " + pessoa.getCpf());
		
		Telefone telefone = pessoa.getTelefones().get(0);
		optional = pessoaRepository.findByTelefonesDddAndTelefonesNumero(telefone.getDdd(), telefone.getNumero());
		
		if(optional.isPresent())
			throw new TelefoneDuplicadoException("Já existe pessoa cadastrada com o telefone (" + telefone.getDdd() + ") " + telefone.getNumero());
		
		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa buscarPorTelefone(Telefone telefone) {	
		Optional<Pessoa> optional = pessoaRepository.findByTelefonesDddAndTelefonesNumero(telefone.getDdd(), telefone.getNumero());
		return optional.orElseThrow(() -> new TelefoneNaoEncontradoException("Não existe pessoa com o telefone (" + telefone.getDdd() +") " + telefone.getNumero()));
	}

}
