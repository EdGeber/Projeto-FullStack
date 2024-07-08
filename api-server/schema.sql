-- DROP TABLE IF EXISTS empresa;  -- comente essa linha se nao quiser deletar os dados
CREATE TABLE IF NOT EXISTS empresa (
	id BIGINT NOT NULL,
	cnpj VARCHAR(14) NOT NULL UNIQUE,
	nome_fantasia VARCHAR(255) NOT NULL,
	cep VARCHAR(8) NOT NULL,
	
	PRIMARY KEY (id),
	INDEX empresa_nome_ind (nome_fantasia)  -- para otimizar filtragem e paginacao por nome
) ENGINE=INNODB;

-- DROP TABLE IF EXISTS fornecedor;
CREATE TABLE IF NOT EXISTS fornecedor (
	id BIGINT NOT NULL,
	cadastro VARCHAR(14) NOT NULL UNIQUE,
	rg VARCHAR(11) UNIQUE,
	data_nascimento DATE,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	cep VARCHAR(8) NOT NULL,
	
	PRIMARY KEY (id),
	INDEX fornecedor_nome_ind (nome),  -- para otimizar a filtragem por nome
	INDEX fornecedor_cadastro_ind (cadastro)  -- para otimizar a filtragem por cadastro
) ENGINE=INNODB;

-- DROP TABLE IF EXISTS fornecimento;
CREATE TABLE IF NOT EXISTS fornecimento (
	id BIGINT NOT NULL,
	empresa_id BIGINT NOT NULL,
	fornecedor_id BIGINT NOT NULL,
	
	PRIMARY KEY (id),
	INDEX fornec_empresa_ind (empresa_id),
	INDEX fornec_fornecedor_ind (fornecedor_id),
	FOREIGN KEY (empresa_id)
		REFERENCES empresa(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (fornecedor_id)
		REFERENCES fornecedor(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
) ENGINE=INNODB;
