package projeto_fullstack.api.error.cnpj;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projeto_fullstack.api.error.cadastro.CadastroValidator;

public class CnpjValidator implements ConstraintValidator<CnpjConstraint, String> {
	@Override
	public void initialize(CnpjConstraint cnpjConstraint) {
	}

	@Override
	public boolean isValid(String cnpj, ConstraintValidatorContext ctx) {
		return CadastroValidator.isCnpjValid(cnpj);
	}

}
