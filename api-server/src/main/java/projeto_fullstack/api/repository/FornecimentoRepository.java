package projeto_fullstack.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import projeto_fullstack.api.entity.Fornecimento;

@Repository
public interface FornecimentoRepository extends JpaRepository<Fornecimento, Long> {
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
				WHERE fornecimento.empresa_id = ?1
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
				WHERE fornecimento.fornecedor_id = ?1
			""";

	@Query(value = GET_FORNECEDORES_QUERY, nativeQuery = true)
	public List<Fornecimento> getFornecedores(Long empresaId);

	@Query(value = GET_EMPRESAS_QUERY, nativeQuery = true)
	public List<Fornecimento> getEmpresas(Long fornecedorId);
}
