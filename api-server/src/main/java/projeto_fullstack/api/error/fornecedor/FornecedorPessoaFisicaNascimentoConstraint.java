package projeto_fullstack.api.error.fornecedor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FornecedorPessoaFisicaNascimentoValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FornecedorPessoaFisicaNascimentoConstraint {
	String message() default "{fornecedorNascimento}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
