package projeto_fullstack.api.error.cep;

import java.time.Duration;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projeto_fullstack.api.ProjetoFullstackApiApplication;

public class CepValidator implements ConstraintValidator<CepConstraint, String> {
	private static final Pattern CEP_PATTERN = Pattern.compile("^\\d{8}$");
	private static final String CEP_API_URL_TEMPLATE = "https://viacep.com.br/ws/{cep}/json/";

	public static ResponseEntity<Map<String, String>> getCepData(String cep) {
		return ProjetoFullstackApiApplication.webClient.get().uri(CEP_API_URL_TEMPLATE, cep).retrieve()
				.toEntity(new ParameterizedTypeReference<Map<String, String>>() {
				}).block(Duration.ofSeconds(5));
	}

	public static boolean isCepValid(ResponseEntity<Map<String, String>> re) {
		return (re.getStatusCode() == HttpStatus.OK) && re.hasBody() && !re.getBody().containsKey("erro");
	}

	public static String getCepUF(ResponseEntity<Map<String, String>> re) {
		return re.getBody().get("uf");
	}

	@Override
	public void initialize(CepConstraint cepConstraint) {
	}

	@Override
	public boolean isValid(String cep, ConstraintValidatorContext ctx) {
		if (!CEP_PATTERN.matcher(cep).matches()) {
			return false;
		}
		ResponseEntity<Map<String, String>> re = getCepData(cep);
		return isCepValid(re);
	}

}
