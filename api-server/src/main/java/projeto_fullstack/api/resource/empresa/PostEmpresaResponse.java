package projeto_fullstack.api.resource.empresa;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostEmpresaResponse {
	Long empresaId;
	List<Long> fornecedorIds;
}
