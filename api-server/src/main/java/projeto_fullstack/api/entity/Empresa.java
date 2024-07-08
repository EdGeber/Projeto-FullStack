package projeto_fullstack.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto_fullstack.api.error.cep.CepConstraint;
import projeto_fullstack.api.error.cnpj.CnpjConstraint;
import projeto_fullstack.api.error.empresa.EmpresaCnpjUnicoConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Empresa")
@EmpresaCnpjUnicoConstraint
//Veja squema.sql
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@CnpjConstraint
	@Column(nullable = false, unique = true, length = 14)
	String cnpj;

	@NotBlank(message = "{fieldBlank}")
	@Column(name = "nome_fantasia", nullable = false, length = 255)
	String nomeFantasia;

	@CepConstraint
	@Column(nullable = false, length = 8)
	String cep;
}
