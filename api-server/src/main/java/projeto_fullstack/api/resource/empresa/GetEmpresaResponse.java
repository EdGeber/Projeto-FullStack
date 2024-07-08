package projeto_fullstack.api.resource.empresa;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import projeto_fullstack.api.entity.Empresa;
import projeto_fullstack.api.entity.Fornecimento;

@Data
@AllArgsConstructor
public class GetEmpresaResponse {
	Empresa empresa;
	List<Fornecimento> fornecimentos;
}
