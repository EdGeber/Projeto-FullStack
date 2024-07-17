package projeto_fullstack.api.error;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler
	protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
		// Controllers podem retornar erros HTTP em casos específicos
		if (ex instanceof ResponseStatusException) {
			ResponseStatusException e = (ResponseStatusException) ex;
			return handleExceptionInternal(e, null, e.getHeaders(), e.getStatusCode(), request);
		} else if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
			Map<String, String> fieldErrors = new HashMap<>();
			List<String> businessErrors = new ArrayList<String>();
			e.getBindingResult().getAllErrors().forEach(error -> {
				if (error instanceof FieldError) {
					String fieldName = ((FieldError) error).getField();
					String errorMessage = error.getDefaultMessage();
					fieldErrors.put(fieldName, errorMessage);
				} else {
					businessErrors.add(error.getDefaultMessage());
				}
			});
			return new ResponseEntity<>(new ErrorObject(fieldErrors, businessErrors), HttpStatus.BAD_REQUEST);
		} else if (ex instanceof ConstraintViolationException) {
			// TODO checar se é uma constraint de classe ou de campo
			ConstraintViolationException e = (ConstraintViolationException) ex;
			Map<String, String> fieldErrors = new HashMap<>();
			List<String> businessErrors = new ArrayList<String>();
			e.getConstraintViolations().forEach(constraintViolation -> {
				fieldErrors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
			});
			return new ResponseEntity<>(new ErrorObject(fieldErrors, businessErrors), HttpStatus.BAD_REQUEST);
		} else {
			// Caso o erro não seja de nenhum dos tipos acima, isso
			// indica um bug
			// TODO: salvar num arquivo de log
			log.debug("REQUEST THAT CAUSED THE ERROR:");
			log.debug(request.toString());
			log.debug("STACK TRACE: ");
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(outputStream);
			ex.printStackTrace(printStream);
			log.debug(outputStream.toString());
			log.debug("END OF STACK TRACE");
			// sempre retorna INTERNAL SERVER ERROR em caso de uma exceção inesperada
			ResponseStatusException e = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
			return handleExceptionInternal(e, null, e.getHeaders(), e.getStatusCode(), request);
		}
	}
}
