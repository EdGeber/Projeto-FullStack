package projeto_fullstack.api.resource.fornecedor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import projeto_fullstack.api.entity.Fornecedor;

@Data
@AllArgsConstructor
public class PostFornecedorRequest {
	@NotNull
	@JsonProperty(required = true)
	Fornecedor fornecedor;
	@JsonProperty(required = false)
	List<Long> empresaIds;
}
