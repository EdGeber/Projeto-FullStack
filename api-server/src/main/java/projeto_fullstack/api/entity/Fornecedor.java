package projeto_fullstack.api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto_fullstack.api.error.cadastro.CadastroConstraint;
import projeto_fullstack.api.error.cep.CepConstraint;
import projeto_fullstack.api.error.fornecedor.FornecedorPessoaFisicaNascimentoConstraint;
import projeto_fullstack.api.error.fornecedor.FornecedorPessoaFisicaParanaMenorConstraint;
import projeto_fullstack.api.error.fornecedor.FornecedorPessoaFisicaRgConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FornecedorPessoaFisicaNascimentoConstraint
@FornecedorPessoaFisicaParanaMenorConstraint
@FornecedorPessoaFisicaRgConstraint
@Table(name = "Fornecedor")
// Veja squema.sql
public class Fornecedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@CadastroConstraint
	@Column(nullable = false, unique = true, length = 14)
	String cadastro;

	// o RG pode ter de 9 a 11 dígitos
	@Column(nullable = true, unique = true, length = 11)
	String rg;

	@Column(name = "data_nascimento", nullable = true)
	LocalDateTime dataNascimento;

	@NotBlank(message = "{fieldBlank}")
	@Column(nullable = false, length = 255)
	String nome;

	@Email(message = "{emailInvalid}")
	@Column(nullable = false, length = 255)
	String email;

	@CepConstraint
	@Column(nullable = false, length = 8)
	String cep;
}
