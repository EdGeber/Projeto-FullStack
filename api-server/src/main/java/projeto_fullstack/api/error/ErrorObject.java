package projeto_fullstack.api.error;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ErrorObject {
	private final Map<String, String> fieldErrors;
	private final List<String> businessErrors;
}
