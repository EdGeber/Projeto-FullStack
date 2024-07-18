package projeto_fullstack.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FornecimentoService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	// query usada para obter informacoes resumidas sobre todos
	// os fornecedores de uma rempresa
	static final String GET_FORNECEDORES_QUERY = """
				SELECT
				fornecimento.id AS id,
				fornecimento.fornecedor_id AS fornecedorId,
				fornecedor.nome AS fornecedorNome
				FROM fornecimento
				JOIN fornecedor
				ON fornecimento.fornecedor_id = fornecedor.id
				WHERE fornecimento.empresa_id = %s
			""";

	// query usada para obter informacoes resumidas sobre todas
	// as empresas de um fornecedor
	static final String GET_EMPRESAS_QUERY = """
				SELECT
				fornecimento.id AS id,
				fornecimento.empresa_id AS empresaId,
				empresa.nome_fantasia AS empresaNome
				FROM fornecimento
				JOIN empresa
				ON fornecimento.empresa_id = empresa.id
				WHERE fornecimento.fornecedor_id = %s
			""";

	public List<Map<String, Object>> getFornecedores(Long empresaId) {
		return jdbcTemplate.queryForList(String.format(GET_FORNECEDORES_QUERY, empresaId));
	}

	@Query(value = GET_EMPRESAS_QUERY)
	public List<Map<String, Object>> getEmpresas(Long fornecedorId) {
		return jdbcTemplate.queryForList(String.format(GET_EMPRESAS_QUERY, fornecedorId));
	}
}
