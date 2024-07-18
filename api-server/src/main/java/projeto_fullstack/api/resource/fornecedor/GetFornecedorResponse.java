package projeto_fullstack.api.resource.fornecedor;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import projeto_fullstack.api.entity.Fornecedor;

@Data
@AllArgsConstructor
public class GetFornecedorResponse {
	Fornecedor fornecedor;
	List<Map<String, Object>> fornecimentos;
}
