package projeto_fullstack.api.error.fornecedor;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projeto_fullstack.api.entity.Fornecedor;
import projeto_fullstack.api.error.cadastro.CadastroValidator;

public class FornecedorPessoaFisicaRgValidator
		implements ConstraintValidator<FornecedorPessoaFisicaRgConstraint, Fornecedor> {
	private static final Pattern RG_PATTERN = Pattern.compile("^\\d{8}(\\d{1,2})?$");

	public void initialize(FornecedorPessoaFisicaNascimentoConstraint constraintAnnotation) {
	}

	public boolean isValid(Fornecedor fornecedor, ConstraintValidatorContext context) {
		if (fornecedor.getCadastro() == null || !CadastroValidator.isCpfValid(fornecedor.getCadastro())) {
			return true;
		}
		return (fornecedor.getRg() != null) && RG_PATTERN.matcher(fornecedor.getRg()).matches();
	}
}
