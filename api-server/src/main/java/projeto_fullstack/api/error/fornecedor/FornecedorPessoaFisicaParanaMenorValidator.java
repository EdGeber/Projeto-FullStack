package projeto_fullstack.api.error.fornecedor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projeto_fullstack.api.entity.Fornecedor;
import projeto_fullstack.api.error.cadastro.CadastroValidator;
import projeto_fullstack.api.error.cep.CepValidator;

public class FornecedorPessoaFisicaParanaMenorValidator
		implements ConstraintValidator<FornecedorPessoaFisicaParanaMenorConstraint, Fornecedor> {

	public void initialize(FornecedorPessoaFisicaParanaMenorConstraint constraintAnnotation) {
	}

	public boolean isValid(Fornecedor fornecedor, ConstraintValidatorContext context) {
		if (fornecedor.getCadastro() == null || !CadastroValidator.isCpfValid(fornecedor.getCadastro())) {
			return true;
		}
		if (fornecedor.getDataNascimento() == null) {
			// nao eh necessario retornar false porque essa condicao ja eh validada
			// por outro Vaildator
			return true;
		}
		if (fornecedor.getCep() == null) {
			return true;
		}
		// TODO: nao fazer essa requisicao duas vezes
		ResponseEntity<Map<String, String>> re = CepValidator.getCepData(fornecedor.getCep());
		if (!CepValidator.isCepValid(re)) {
			return true;
		}
		String uf = CepValidator.getCepUF(re);
		if (!uf.equals("PR")) {
			return true;
		}
		return ChronoUnit.YEARS.between(fornecedor.getDataNascimento(), LocalDateTime.now()) >= 18L;
	}
}
