package projeto_fullstack.api.resource.empresa;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import projeto_fullstack.api.entity.Empresa;

@Data
@AllArgsConstructor
public class GetEmpresaResponse {
	Empresa empresa;
	List<Map<String, Object>> fornecimentos;
}
