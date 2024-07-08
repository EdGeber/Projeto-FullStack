package projeto_fullstack.api.error.cadastro;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CadastroValidator implements ConstraintValidator<CadastroConstraint, String> {
	private static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{14}$");
	private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

	public static boolean isCnpjValid(String cnpj) {
		return CNPJ_PATTERN.matcher(cnpj).matches();
	}

	public static boolean isCpfValid(String cpf) {
		return CPF_PATTERN.matcher(cpf).matches();
	}
	
	public static boolean isCadastroValid(String cadastro) {
		return isCnpjValid(cadastro) || isCpfValid(cadastro);
	}

	@Override
	public void initialize(CadastroConstraint cadastroConstraint) {
	}

	@Override
	public boolean isValid(String cadastro, ConstraintValidatorContext ctx) {
		return isCadastroValid(cadastro);
	}

}
