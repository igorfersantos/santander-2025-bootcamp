DELIMITER $$
CREATE PROCEDURE prc_insert_employee(
    OUT parameter_id BIGINT,
    IN parameter_name VARCHAR(150),
    IN parameter_salary DECIMAL(10,2),
    IN parameter_birthday TIMESTAMP
)
BEGIN

    INSERT INTO employees (name, salary, birthday)
    VALUES (parameter_name, parameter_salary, parameter_birthday);

    SET parameter_id = LAST_INSERT_ID();

END $$