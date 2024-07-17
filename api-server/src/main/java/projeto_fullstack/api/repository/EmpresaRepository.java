package projeto_fullstack.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import projeto_fullstack.api.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	// https://stackoverflow.com/questions/3799193/mysql-data-best-way-to-implement-paging
	@Query(value = "SELECT * FROM empresa ORDER BY nome_fantasia LIMIT ?1", nativeQuery = true)
	public Iterable<Empresa> getFirstPage(int limit);

	@Query(value = "SELECT * FROM empresa WHERE nome_fantasia > ?1 ORDER BY nome_fantasia LIMIT ?2", nativeQuery = true)
	public Iterable<Empresa> getPage(String lastSeenName, int limit);

	public List<Empresa> findByCnpj(String cnpj);

	public List<Empresa> findByNomeFantasiaContaining(String nome);

	public List<Empresa> findByCnpjContaining(String cnpj);
}
