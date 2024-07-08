package projeto_fullstack.api.error.fornecedor;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projeto_fullstack.api.entity.Fornecedor;
import projeto_fullstack.api.error.cadastro.CadastroValidator;

public class FornecedorPessoaFisicaNascimentoValidator
		implements ConstraintValidator<FornecedorPessoaFisicaNascimentoConstraint, Fornecedor> {

	public void initialize(FornecedorPessoaFisicaNascimentoConstraint constraintAnnotation) {
	}

	public boolean isValid(Fornecedor fornecedor, ConstraintValidatorContext context) {
		if (fornecedor.getCadastro() == null || !CadastroValidator.isCpfValid(fornecedor.getCadastro())) {
			return true;
		}
		return (fornecedor.getDataNascimento() != null) && !fornecedor.getDataNascimento().isAfter(LocalDateTime.now());
	}
}
