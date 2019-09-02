CREATE TABLE endereco (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  logradouro VARCHAR(255),
  numero INT,
  complemento VARCHAR(255),
  bairro VARCHAR(255),
  cidade VARCHAR(255),
  estado VARCHAR(2),
  id_pessoa BIGINT NOT NULL,
  FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
);