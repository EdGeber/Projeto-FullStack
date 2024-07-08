package projeto_fullstack.api.resource.fornecedor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import projeto_fullstack.api.entity.Fornecedor;
import projeto_fullstack.api.entity.Fornecimento;

@Data
@AllArgsConstructor
public class GetFornecedorResponse {
	Fornecedor fornecedor;
	List<Fornecimento> fornecimentos;
}
