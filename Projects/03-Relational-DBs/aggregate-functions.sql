-- Aggregate functions in SQL are used to perform calculations on a set of values and return a single value. Common aggregate functions include:
-- 
-- - `COUNT()`: Counts the number of rows that match a specified condition.
-- - `SUM()`: Calculates the total sum of a numeric column.
-- - `AVG()`: Computes the average value of a numeric column.
-- - `MIN()`: Returns the smallest value in a set.
-- - `MAX()`: Returns the largest value in a set.
-- 
-- These functions are often used in conjunction with the `GROUP BY` clause to group rows that have the same values in specified columns into summary rows.


-- Uso de COUNT(): Contar a quantidade total de reservas válidas (considerando apenas que a reserva possui um usuário válido)
SELECT COUNT(*) as total_reservas
FROM usuarios
INNER JOIN reservas ON reservas.ID_USUARIO = usuarios.ID;

-- Uso de MAX(): Calcular qual a maior idade entre todos os nossos usuários

-- 
-- SELECT MAX(TIMESTAMPDIFF(YEAR, data_nascimento, CURRENT_DATE())) AS maior_idade from usuarios;
-- In PostgreSQL, the `TIMESTAMPDIFF` function is not available. Instead, we can use the `AGE` function to calculate the difference between two dates and then extract the year from that interval. 
-- 
-- Here’s how you can transform the query into a valid PostgreSQL query:
-- 
-- 1. Use the `AGE` function to calculate the age based on `data_nascimento` and the current date.
-- 2. Extract the year from the interval returned by the `AGE` function.
-- 3. Use the `MAX` function to get the maximum age.
-- 
-- Here’s the equivalent SQL query:
SELECT MAX(EXTRACT(YEAR FROM AGE("data_nascimento"))) AS maior_idade FROM "usuarios";

-- Teste de uso isolado da função EXTRACT() E AGE()
SELECT id, nome, data_nascimento, EXTRACT(YEAR FROM AGE("data_nascimento")) AS idade from usuarios;

-- Teste com função que retorna informações da versão atual do banco
SELECT version();

-- GROUP BY: Uso de agrupamento de resultados com funções agregadoras via GROUP BY
SELECT * FROM reservas;

INSERT INTO reservas VALUES (4, 3, 4, '2023-09-05');

SELECT COUNT(*), reservas.ID_DESTINO FROM reservas
GROUP BY reservas.id_destino;

-- ORDER BY: Ordenação de resultados com funções agregadoras via ORDER BY
SELECT reservas.ID_DESTINO, COUNT(*) AS qtd_reservas FROM reservas
GROUP BY reservas.id_destino
ORDER BY qtd_reservas DESC;