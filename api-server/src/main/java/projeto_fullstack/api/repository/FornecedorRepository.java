package projeto_fullstack.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import projeto_fullstack.api.entity.Empresa;
import projeto_fullstack.api.entity.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
	// https://stackoverflow.com/questions/3799193/mysql-data-best-way-to-implement-paging
	@Query(value = "SELECT * FROM fornecedor ORDER BY nome LIMIT ?1", nativeQuery = true)
	public Iterable<Fornecedor> getFirstPage(int limit);

	@Query(value = "SELECT * FROM fornecedor WHERE nome > ?1 ORDER BY nome LIMIT ?2", nativeQuery = true)
	public Iterable<Fornecedor> getPage(String lastSeenName, int limit);

	public List<Fornecedor> findByCadastro(String cadastro);

	public List<Empresa> findByNomeContaining(String nome);

	public List<Empresa> findByCadastroContaining(String cadastro);
}
