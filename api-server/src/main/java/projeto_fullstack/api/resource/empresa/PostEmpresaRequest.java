package projeto_fullstack.api.resource.empresa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import projeto_fullstack.api.entity.Empresa;

@Data
public class PostEmpresaRequest {
	@JsonProperty(required = true)
	@NotNull
	Empresa empresa;
	@JsonProperty(required = false)
	List<Long> fornecedorIds;
}
