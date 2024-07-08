package projeto_fullstack.api.resource.fornecedor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostFornecedorResponse {
	Long fornecedorId;
	List<Long> empresaIds;
}
