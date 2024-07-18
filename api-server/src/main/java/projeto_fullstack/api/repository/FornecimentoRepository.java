package projeto_fullstack.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto_fullstack.api.entity.Fornecimento;

public interface FornecimentoRepository extends JpaRepository<Fornecimento, Long> {

}
