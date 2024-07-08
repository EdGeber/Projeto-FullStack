package projeto_fullstack.api.error.fornecedor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FornecedorCadastroUnicoValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FornecedorCadastroUnicoConstraint {
	String message() default "{cadastroExistsFornecedor}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
