package projeto_fullstack.api.error.cadastro;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CadastroValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CadastroConstraint {
	String message() default "{cadastroInvalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
