package br.com.dio.persistence.dao.employee;

import br.com.dio.persistence.ConnectionUtil;
import br.com.dio.persistence.dao.EntityDAO;
import br.com.dio.persistence.entity.EmployeeEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.dio.persistence.DateUtil.formatOffsetDateTime;
import static java.time.ZoneOffset.UTC;


/**
 * A {@link EntityDAO} implementation for {@link EmployeeEntity} entities.
 * @see EmployeeEntity
 * @see EntityDAO
 */
public class EmployeeDAO implements EntityDAO<EmployeeEntity> {
    private final String TABLE_NAME = "employees";

    /**
     * Inserts a new employee entity into the database.
     *
     * @param entity The employee entity to be inserted.
     */
    @Override
    public void insert(final EmployeeEntity entity) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            var sql = "INSERT INTO %s (name, salary, birthday) VALUES (" +
                    "'%s', %.2f, '%s'" +
                    ")";
            var finalSql = sql.formatted(
                    TABLE_NAME,
                    entity.getName(),
                    entity.getSalary(),
                    formatOffsetDateTime(entity.getBirthday())
            );
            statement.executeUpdate(finalSql);
            System.out.printf(
                    "Foram afetados %s registros na tabela %s\n",
                    statement.getUpdateCount(),
                    TABLE_NAME
            );
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts a list of new employee entities into the database. This method is more efficient
     * than inserting each entity individually with {@link #insert(EmployeeEntity)}.
     *
     * @param entities The employee entities to be inserted.
     */
    @Override
    public void insertMany(final List<EmployeeEntity> entities) {
    }

    /**
     * Updates an existing employee entity in the database.
     *
     * @param entity The employee entity to be updated.
     */
    @Override
    public void update(final EmployeeEntity entity) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            var sql = "UPDATE %s SET name = '%s', salary = %s, birthday = '%s' " +
                    "WHERE id = %s";
            var finalSql = sql.formatted(
                    TABLE_NAME,
                    entity.getName(),
                    entity.getSalary(),
                    formatOffsetDateTime(entity.getBirthday()),
                    entity.getId()
            );
            statement.executeUpdate(finalSql);
            System.out.printf(
                    "Foram afetados %s registros na tabela %s\n",
                    statement.getUpdateCount(),
                    TABLE_NAME
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deletes an employee entity from the database based on its ID.
     *
     * @param id The ID of the employee entity to be deleted.
     */
    @Override
    public void delete(final long id) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            final String sql = "DELETE FROM employees WHERE id = %s";
            statement.executeUpdate(sql.formatted(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns a list of all employee entities in the database.
     *
     * @return A list of all employee entities in the database.
     */
    @Override
    public List<EmployeeEntity> findAll() {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            final String sql = "SELECT * FROM %s";
            final ResultSet resultSet = statement.executeQuery(sql.formatted(TABLE_NAME));

            while (resultSet.next()) {
                var employee = new EmployeeEntity();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSalary(resultSet.getBigDecimal("salary"));
                var birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
                var birthday = OffsetDateTime.ofInstant(birthdayInstant, UTC);
                employee.setBirthday(birthday);
                employeeEntities.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeEntities;
    }


    /**
     * Finds an employee entity by its ID in the database.
     *
     * @param id The ID of the employee entity to be found.
     * @return An {@link Optional} containing the employee entity with the given ID,
     * or an empty {@link Optional} if no such entity exists.
     */
    @Override
    public Optional<EmployeeEntity> findById(final long id) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            final String sql = "SELECT * FROM %s WHERE id = %s";
            final ResultSet resultSet = statement.executeQuery(sql.formatted(TABLE_NAME, id));

            if (resultSet.next()) {
                var employee = new EmployeeEntity();
                employee.setId(id);
                employee.setName(resultSet.getString("name"));
                employee.setSalary(resultSet.getBigDecimal("salary"));
                var birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
                var birthday = OffsetDateTime.ofInstant(birthdayInstant, UTC);
                employee.setBirthday(birthday);
                return Optional.of(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
