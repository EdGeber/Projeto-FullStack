package projeto_fullstack.api.error.empresa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EmpresaCnpjUnicoValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmpresaCnpjUnicoConstraint {
	String message() default "{cnpjExistsEmpresa}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
