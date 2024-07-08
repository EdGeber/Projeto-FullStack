package projeto_fullstack.api.error.fornecedor;

import java.util.Optional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projeto_fullstack.api.entity.Fornecedor;
import projeto_fullstack.api.error.cadastro.CadastroValidator;
import projeto_fullstack.api.resource.fornecedor.FornecedorResource;

public class FornecedorCadastroUnicoValidator
		implements ConstraintValidator<FornecedorCadastroUnicoConstraint, Fornecedor> {

	public void initialize(FornecedorCadastroUnicoConstraint constraintAnnotation) {
	}

	public boolean isValid(Fornecedor fornecedor, ConstraintValidatorContext context) {
		if (!CadastroValidator.isCadastroValid(fornecedor.getCadastro())) {
			return true;
		}
		Optional<Fornecedor> result = FornecedorResource.fornecedorRepository.findByCadastro(fornecedor.getCadastro());
		return result.isEmpty() || (result.get().getId() == fornecedor.getId());
	}

}
