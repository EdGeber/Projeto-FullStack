package projeto_fullstack.api.error.empresa;

import java.util.Optional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projeto_fullstack.api.entity.Empresa;
import projeto_fullstack.api.error.cadastro.CadastroValidator;
import projeto_fullstack.api.resource.empresa.EmpresaResource;

public class EmpresaCnpjUnicoValidator implements ConstraintValidator<EmpresaCnpjUnicoConstraint, Empresa> {

	public void initialize(EmpresaCnpjUnicoConstraint constraintAnnotation) {
	}

	public boolean isValid(Empresa empresa, ConstraintValidatorContext context) {
		if (!CadastroValidator.isCnpjValid(empresa.getCnpj())) {
			return true;
		}
		Optional<Empresa> result = EmpresaResource.empresaRepository.findByCnpj(empresa.getCnpj());
		return result.isEmpty() || (result.get().getId() == empresa.getId());
	}

}
