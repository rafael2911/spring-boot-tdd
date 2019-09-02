package br.com.crcarvalho.catalogo.repository.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.crcarvalho.catalogo.model.Pessoa;
import br.com.crcarvalho.catalogo.repository.filtro.PessoaFiltro;

@Component
public class PessoaRepositoryQueriesImpl implements PessoaRepositoryQueries {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Pessoa> filtrar(PessoaFiltro filtro) {
		final StringBuilder sb = new StringBuilder();
		final Map<String, Object> params = new HashMap<>();
		
		sb.append("SELECT p FROM Pessoa p WHERE 1=1");
		
		
		preencherNome(filtro, sb, params);
		
		preencherCpf(filtro, sb, params);
		
		TypedQuery<Pessoa> query = em.createQuery(sb.toString(), Pessoa.class);
		
		preencherParametrosDaQuery(params, query);
		
		return query.getResultList();
	}

	private void preencherCpf(PessoaFiltro filtro, final StringBuilder sb, final Map<String, Object> params) {
		if(StringUtils.hasText(filtro.getCpf())) {
			sb.append(" AND p.cpf LIKE :cpf");
			params.put("cpf", "%" + filtro.getCpf() + "%");
		}
	}

	private void preencherNome(PessoaFiltro filtro, final StringBuilder sb, final Map<String, Object> params) {
		if(StringUtils.hasText(filtro.getNome())) {
			sb.append(" AND p.nome LIKE :nome");
			params.put("nome", "%" + filtro.getNome() + "%");
		}
	}

	private void preencherParametrosDaQuery(final Map<String, Object> params, TypedQuery<Pessoa> query) {
//		for(Entry<String, Object> param : params.entrySet()) {
//			query.setParameter(param.getKey(), param.getValue());
//		}
		params.forEach((s, o) -> query.setParameter(s, o));
	}

}
