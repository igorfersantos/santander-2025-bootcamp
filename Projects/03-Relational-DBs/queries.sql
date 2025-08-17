INSERT 
	INTO 
	public.usuarios (id,
	nome,
	email,
	data_nascimento,
	ENDERECO)
VALUES (1, 'Igor Fernandes', 'teste@teste.com', '1992-10-05', 'Av cu do mundo - Fodase/SP');

-- Inserts --
INSERT INTO usuarios (id, nome, email, data_nascimento, endereco) VALUES 
(1, 'João Silva', 'joao@example.com', '1990-05-15', 'Rua A, 123, Cidade X, Estado Y'),
(2, 'Maria Santos', 'maria@example.com', '1985-08-22', 'Rua B, 456, Cidade Y, Estado Z'),
(3, 'Pedro Souza', 'pedro@example.com', '1998-02-10', 'Avenida C, 789, Cidade X, Estado Y');

INSERT INTO destinos (id, nome, descricao) VALUES 
(1, 'Praia das Tartarugas', 'Uma bela praia com areias brancas e mar cristalino'),
(2, 'Cachoeira do Vale Verde', 'Uma cachoeira exuberante cercada por natureza'),
(3, 'Cidade Histórica de Pedra Alta', 'Uma cidade rica em história e arquitetura');

INSERT INTO reservas (id, id_usuario, id_destino, data, status) VALUES 
(1,2, 2, '2023-07-10', 'confirmada'),
(2,2, 1, '2023-08-05', 'pendente'),
(3,3, 3, '2023-09-20', 'cancelada');

-- Selects --

-- Selecionar todos os registros da tabela "usuarios"
SELECT * FROM usuarios;

-- Selecionar apenas o nome e o email dos usuários
SELECT nome, email FROM usuarios;

-- Selecionar os usuários que possuem o nome "João Silva" ou que contenham "Maria"
SELECT * FROM usuarios WHERE nome = 'João Silva' OR nome LIKE '%Maria%';

-- Selecionar os usuários que nasceram antes de uma determinada data
SELECT * FROM usuarios WHERE data_nascimento < '1990-01-01';

-- Like
SELECT * FROM usuarios WHERE nome LIKE '%Silva%';
SELECT * FROM usuarios WHERE nome LIKE 'Jo_o%';

-- Update --
UPDATE usuarios SET endereco = 'Nova Rua, 123' WHERE email = 'joao@example.com';

-- delete --
DELETE FROM reservas WHERE status = 'cancelada';

-- alter table --
ALTER TABLE usuarios
	ALTER COLUMN endereco TYPE VARCHAR(100);

ALTER TABLE usuarios 
ADD PRIMARY KEY (id);

ALTER TABLE destinos
ADD PRIMARY KEY (id);

ALTER TABLE reservas 
ADD PRIMARY KEY (id);

-- Adicionando chave estrangeira na tabela "reservas" referenciando a tabela "usuarios"
ALTER TABLE reservas
ADD CONSTRAINT fk_reservas_usuarios
FOREIGN KEY (id_usuario) REFERENCES usuarios(id);

-- Adicionando chave estrangeira na tabela "reservas" referenciando a tabela "destinos"
ALTER TABLE reservas
ADD CONSTRAINT fk_reservas_destinos
	ON DELETE CASCADE
FOREIGN KEY (id_destino) REFERENCES destinos(id);

-- testando on delete
DELETE FROM usuarios where id = 1;

-- Adicionar colunas de endereço à tabela "Usuarios"
ALTER TABLE Usuarios
ADD rua VARCHAR(100),
ADD numero VARCHAR(10),
ADD cidade VARCHAR(50),
ADD estado VARCHAR(50);

-- Transfere os dados de endereço da única coluna endereço
-- para agora os 4 novos campos em comformidade com a 1FN.
-- Script original em MySQL
-- https://github.com/pamelaborges/dio-bd-relacional/blob/main/aula4-1.sql
UPDATE usuarios 
SET 
    rua = TRIM(SPLIT_PART(endereco, ',', 1)),
    numero = TRIM(SPLIT_PART(endereco, ',', 2)),
    cidade = TRIM(SPLIT_PART(endereco, ',', 3)),
    estado = TRIM(SPLIT_PART(endereco, ',', 4));

ALTER TABLE usuarios 
DROP COLUMN endereco;