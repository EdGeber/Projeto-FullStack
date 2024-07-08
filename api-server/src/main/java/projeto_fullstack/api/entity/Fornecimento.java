package projeto_fullstack.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Fornecimento")
// Veja squema.sql
public class Fornecimento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(required = false)
	Long id;

	/*
	 * Prefiro definir o squema.sql em vez de usar o spring data nesse caso.
	 * Entretanto, essas seriam as anotacoes usadas nesse caso
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "empresa_id", nullable = false)
	 * 
	 * @JsonIgnore
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE)
	 */
	@Column(name = "empresa_id")
	@JsonProperty(required = false)
	Long empresaId;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "fornecedor_id")
	 * 
	 * @JsonIgnore
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE)
	 */
	@Column(name = "fornecedor_id")
	@JsonProperty(required = false)
	Long fornecedorId;

	// novos atributos do relacionamento podem ser adicionados se necessario

	@Transient
	@JsonProperty(required = false)
	String empresaNome;

	@Transient
	@JsonProperty(required = false)
	String fornecedorNome;

}
