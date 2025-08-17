-- Índices é um mecanismo de SGBDs para otimizar a velocidade de leitura dos dados em uma segunda tabela, com uma estrutura de dados otimizada 
-- para pesquisa, como uma árvore B+ ou uma função hash, tendo como trade-off a necessidade de armazenar um maior volume de dados.
--
-- Este recurso é útil para que pesquisas que são frequentemente realizadas sejam rapidamente recuperadas, gerando um ganho de performance
-- ao evitar o reprocessamento desnecessário dos dados requeridos pela consulta

-- Em um cenário ideal os índices são identificados logo na concepção do sistema e/ou da modelagem do sistema
-- Porém, em muitas situações não é possível de prever ou simplesmente não foi pensado pelos desenvolvedores originais
-- esses cenários onde os índices poderiam ser usados.

-- Para contornar isso, a maioria dos bancos possui uma ferramenta de análise do plano de execução, que permite examinar as operações realizadas
-- como tabelas acessadas, índices utilizados entre outras informações para que possamos identificar possíveis melhorias de desempenho com índices
-- ou outras mudanças que o desenvolvedor julgar importante para melhoria do desempenho do banco.

-- Inserindo massa de dados --

CREATE TABLE usuarios2 (
	id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	nome varchar(255) NOT NULL,
	email varchar(100) UNIQUE NOT NULL,
	data_nascimento date NOT NULL,
	rua varchar(100) NULL,
	numero varchar(10) NULL,
	cidade varchar(50) NULL,
	estado varchar(50) NULL
);

INSERT INTO PUBLIC.USUARIOS2 (nome, email, data_nascimento, rua, numero, cidade, estado) VALUES 
(SELECT nome, email, data_nascimento, rua, numero, cidade, estado FROM usuarios);

INSERT INTO usuarios (nome, email, data_nascimento, rua) VALUES
('João Silva', 'joao.silva@example.com', '1990-01-01', 'Rua A'),
('Maria Santos', 'maria.santos@example.com', '1992-03-15', 'Rua B'),
('Pedro Almeida', 'pedro.almeida@example.com', '1985-07-10', 'Rua C'),
('Ana Oliveira', 'ana.oliveira@example.com', '1998-12-25', 'Rua D'),
('Carlos Pereira', 'carlos.pereira@example.com', '1991-06-05', 'Rua E'),
('Laura Mendes', 'laura.mendes@example.com', '1994-09-12', 'Rua F'),
('Fernando Santos', 'fernando.santos@example.com', '1988-02-20', 'Rua G'),
('Mariana Costa', 'mariana.costa@example.com', '1997-11-30', 'Rua H'),
('Ricardo Rodrigues', 'ricardo.rodrigues@example.com', '1993-04-18', 'Rua I'),
('Camila Alves', 'camila.alves@example.com', '1989-08-08', 'Rua J'),
('Bruno Carvalho', 'bruno.carvalho@example.com', '1995-03-25', 'Rua K'),
('Amanda Silva', 'amanda.silva@example.com', '1996-12-02', 'Rua L'),
('Paulo Mendonça', 'paulo.mendonca@example.com', '1999-07-20', 'Rua M'),
('Larissa Oliveira', 'larissa.oliveira@example.com', '1987-10-15', 'Rua N'),
('Fernanda Sousa', 'fernanda.sousa@example.com', '1992-05-08', 'Rua O'),
('Gustavo Santos', 'gustavo.santos@example.com', '1993-09-18', 'Rua P'),
('Helena Costa', 'helena.costa@example.com', '1998-02-22', 'Rua Q'),
('Diego Almeida', 'diego.almeida@example.com', '1991-11-27', 'Rua R'),
('Juliana Lima', 'juliana.lima@example.com', '1997-04-05', 'Rua S'),
('Rafaela Silva', 'rafaela.silva@example.com', '1996-01-10', 'Rua T'),
('Lucas Pereira', 'lucas.pereira@example.com', '1986-08-30', 'Rua U'),
('Fábio Rodrigues', 'fabio.rodrigues@example.com', '1989-03-12', 'Rua V'),
('Isabela Santos', 'isabela.santos@example.com', '1994-12-07', 'Rua W'),
('André Alves', 'andre.alves@example.com', '1995-09-28', 'Rua X'),
('Clara Carvalho', 'clara.carvalho@example.com', '1990-02-15', 'Rua Y'),
('Roberto Mendes', 'roberto.mendes@example.com', '1992-07-21', 'Rua Z'),
('Mariana Oliveira', 'mariana.oliveira@example.com', '1997-05-03', 'Av. A'),
('Gustavo Costa', 'gustavo.costa@example.com', '1998-11-16', 'Av. B'),
('Lara Sousa', 'lara.sousa@example.com', '1993-06-09', 'Av. C'),
('Pedro Lima', 'pedro.lima@example.com', '1996-09-27', 'Av. D');