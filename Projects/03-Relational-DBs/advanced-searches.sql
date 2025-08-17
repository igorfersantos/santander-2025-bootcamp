
insert
	into
	usuarios (
	id,
		nome,
		email,
		data_nascimento,
		rua,
		numero,
		cidade,
		estado
	)
values (
4,
	'sem reservas',
	'dio@teste.com',
	'1992-05-05',
	'rua',
	'12',
	'cidade',
	'estado'
);

SELECT * FROM usuarios;

-- Joins
-- https://i.sstatic.net/UI25E.jpg

-- Inner Join
SELECT * FROM usuarios
INNER JOIN reservas ON usuarios.id = id_usuario
INNER JOIN destinos ON destinos.id = reservas.id_destino;

-- Left Join
-- Essa query retorna o usuário sem reservas
SELECT * FROM usuarios
LEFT JOIN reservas ON usuarios.id = id_usuario

-- Right Join
-- Preparação para um exemplo onde possuimos um destino sem reservas para explicar o right join
SELECT * FROM destinos;
INSERT INTO PUBLIC.DESTINOS (id, NOME, DESCRICAO ) VALUES (4, 'Destino sem servas', 'Destino sem reservas');
SELECT * FROM reservas;

-- Essa query retorna todos os destinos mesmo que não possuam reservas
SELECT * FROM reservas
RIGHT JOIN destinos ON reservas.ID_DESTINO = destinos.ID;
|id |id_usuario|id_destino|data      |status    |id |nome                          |descricao                                         |
|---|----------|----------|----------|----------|---|------------------------------|--------------------------------------------------|
|2  |2         |1         |2023-08-05|pendente  |1  |Praia das Tartarugas          |Uma bela praia com areias brancas e mar cristalino|
|1  |2         |2         |2023-07-10|confirmada|2  |Cachoeira do Vale Verde       |Uma cachoeira exuberante cercada por natureza     |
|3  |3         |3         |2023-09-20|cancelada |3  |Cidade Histórica de Pedra Alta|Uma cidade rica em história e arquitetura         |
|   |          |          |          |          |4  |Destino sem servas            |Destino sem reservas                              |


-- Full Join
-- Criar exemplo depois


-- Sub queries

-- Exemplo onde selecionamos destinos que não possuem reservas
SELECT * FROM destinos
WHERE id NOT IN (SELECT id_destino FROM reservas);

-- Exemplo onde selecionamos usuários que não possuem reservas
SELECT * FROM usuarios
WHERE id NOT IN (SELECT id_usuario FROM reservas);

-- Utilizando sub-queries dentro das consultas (SELECT)
-- aqui utilizamos uma subquery direto na consulta para trazer a quantidade de reservas realizadas por um único usuário
-- e damos o nome de total_reservas como nome de coluna
SELECT nome, (SELECT COUNT(*) FROM reservas WHERE reservas.id_usuario = usuarios.id) AS total_reservas FROM usuarios;