package br.com.dio.persistence.dao.employee;

import br.com.dio.persistence.ConnectionUtil;
import br.com.dio.persistence.dao.EntityDAO;
import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static java.util.TimeZone.LONG;

/**
 * Implementation of {@link EntityDAO} for performing CRUD operations on employee entities
 * using parameterized SQL queries to prevent SQL injection.
 *
 * <p>This class provides methods to interact with the employees table in the database,
 * including basic CRUD operations and batch operations for better performance.</p>
 *
 * <p>All database operations are performed using prepared statements to ensure security
 * and prevent SQL injection attacks.</p>
 *
 * @see EntityDAO
 * @see EmployeeEntity
 */
public class EmployeeParamDAO implements EntityDAO<EmployeeEntity> {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeParamDAO.class);
    private static final String EMPLOYEE_ID = "employee_id";
    private static final String EMPLOYEE_NAME = "name";
    private static final String EMPLOYEE_SALARY = "salary";
    private static final String EMPLOYEE_BIRTHDAY = "birthday";
    private static final String CONTACT_ID = "contact_id";
    private static final String CONTACT_DESCRIPTION = "description";
    private static final String CONTACT_TYPE = "type";
    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the provided entity is null
     * @throws DataAccessException      if a database access error occurs
     */
    @Override
    public void insert(final EmployeeEntity entity) {
        validateEntity(entity);

        final String sql = "INSERT INTO employees (name, salary, birthday) VALUES (?, ?, ?)";

        try (var connection = ConnectionUtil.getConnection();
             var statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            setEmployeeParameters(statement, entity);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating employee failed, no rows affected.");
            }

            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new DataAccessException("Creating employee failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            logger.error("Error inserting employee: {}", entity, ex);
            throw new DataAccessException("Error inserting employee", ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the provided list is null or empty
     * @throws DataAccessException      if a database access error occurs
     */
    @Override
    public void insertMany(final List<EmployeeEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            throw new IllegalArgumentException("Employee list cannot be null or empty");
        }

        final String sql = "INSERT INTO employees (name, salary, birthday) VALUES (?, ?, ?)";

        try (var connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);

            try (var statement = connection.prepareStatement(sql)) {
                for (var entity : entities) {
                    validateEntity(entity);
                    setEmployeeParameters(statement, entity);
                    statement.addBatch();
                }

                int[] updateCounts = statement.executeBatch();
                connection.commit();
                logger.debug("Successfully inserted {} employees", updateCounts.length);

            } catch (SQLException ex) {
                connection.rollback();
                logger.error("Batch insert failed, transaction rolled back", ex);
                throw new DataAccessException("Error during batch insert of employees", ex);
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Error establishing database connection", ex);
        }
    }

    /**
     * Inserts an employee entity into the database using a stored procedure.
     * <p>
     * The stored procedure is called with the entity's name, salary, and birthday
     * as parameters. The ID of the inserted entity is retrieved from the
     * procedure's output parameter.
     * <p>
     * Note that the procedure is defined as follows:
     * <pre>
     * CREATE PROCEDURE prc_insert_employee(
     *     OUT id LONG,
     *     IN name VARCHAR(200),
     *     IN salary DECIMAL(10, 2),
     *     IN birthday TIMESTAMP
     * )
     * BEGIN
     *     INSERT INTO employees (name, salary, birthday) VALUES (name, salary, birthday);
     *     SET id = LAST_INSERT_ID();
     * END;
     * </pre>
     *
     * @param entity The employee entity to be inserted.
     */
    public void insertWithProcedure(final EmployeeEntity entity) {
        validateEntity(entity);

        final String sql = "call prc_insert_employee(?, ?, ?, ?)";

        try (var connection = ConnectionUtil.getConnection();
             var statement = connection.prepareCall(sql)) {

            statement.registerOutParameter(1, LONG);
            statement.setString(2, entity.getName());
            statement.setBigDecimal(3, entity.getSalary());
            statement.setTimestamp(4,
                    Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(UTC).toLocalDateTime())
            );
            statement.execute();
            entity.setId(statement.getLong(1));
        } catch (SQLException ex) {
            logger.error("Error inserting employee with procedure: {}", entity, ex);
            throw new DataAccessException("Error inserting employee with procedure", ex);
        }
    }

    /**
     * Updates an existing employee entity in the database.
     * <p>
     * The update is done using a prepared statement with the entity's name, salary,
     * and birthday as parameters. The ID of the entity is used to identify the
     * record to be updated.
     * <p>
     * If successful, prints the number of records affected in the database.
     *
     * @param entity The employee entity to be updated.
     */
    @Override
    public void update(final EmployeeEntity entity) {
        validateEntity(entity);

        final String sql = "UPDATE employees SET name = ?, salary = ?, birthday = ? WHERE id = ?";

        try (var connection = ConnectionUtil.getConnection();
             var statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getSalary());
            statement.setTimestamp(3,
                    Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(UTC).toLocalDateTime())
            );
            statement.setLong(4, entity.getId());
            int affectedRows = statement.executeUpdate();
            logger.debug("Updated {} employee records", affectedRows);
        } catch (SQLException ex) {
            logger.error("Error updating employee: {}", entity, ex);
            throw new DataAccessException("Error updating employee", ex);
        }
    }

    /**
     * Deletes an employee entity from the database based on its ID.
     * <p>
     * Delete is done using a prepared statement with the ID of the entity
     * to be deleted as a parameter. The method prints the number of records
     * affected in the database if successful.
     *
     * @param id The ID of the employee entity to be deleted.
     */
    @Override
    public void delete(final long id) {
        final String sql = "DELETE FROM employees WHERE id = ?";

        try (var connection = ConnectionUtil.getConnection();
             var statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            logger.debug("Deleted {} employee records", affectedRows);
        } catch (SQLException ex) {
            logger.error("Error deleting employee with ID: {}", id, ex);
            throw new DataAccessException("Error deleting employee", ex);
        }
    }

    /**
     * Returns a list of all employee entities in the database.
     * <p>
     * The entities are retrieved from the database by executing a SELECT
     * statement with an ORDER BY clause to sort the entities by name.
     * The method prints stack traces if it encounters any errors.
     *
     * @return A list of all employee entities in the database, sorted by name.
     */
    @Override
    public List<EmployeeEntity> findAll() {
        List<EmployeeEntity> entities = new ArrayList<>();
        final String sql = "SELECT * FROM employees ORDER BY name";

        try (var connection = ConnectionUtil.getConnection();
             var statement = connection.createStatement()) {

            statement.executeQuery(sql);
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var entity = new EmployeeEntity();
                entity.setId(resultSet.getLong(EMPLOYEE_ID));
                entity.setName(resultSet.getString(EMPLOYEE_NAME));
                entity.setSalary(resultSet.getBigDecimal(EMPLOYEE_SALARY));
                var birthdayInstant = resultSet.getTimestamp(EMPLOYEE_BIRTHDAY).toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                entities.add(entity);
            }
        } catch (SQLException ex) {
            logger.error("Error retrieving all employees", ex);
            throw new DataAccessException("Error retrieving all employees", ex);
        }
        return entities;
    }

    /**
     * Finds an employee entity by its ID in the database.
     * <p>
     * The method executes a SELECT statement with the given ID as a parameter,
     * and retrieves the entity from the result set. If the entity is not found,
     * the method returns an empty Optional.
     * <p>
     * If it encounters any errors during the execution of the statement,
     * the method prints the stack trace.
     *
     * @param id The ID of the employee entity to be found.
     * @return An Optional containing the employee entity with the given ID,
     * or an empty Optional if no such entity exists.
     */
    @Override
    public Optional<EmployeeEntity> findById(final long id) {
        final String sql = "SELECT e.id employee_id, e.name, e.salary, e.birthday, c.id contact_id, c.description," +
                " c.type" +
                " FROM employees e " +
                "INNER JOIN " +
                "contacts c ON c.employee_id = e.id WHERE e.id = ?";


        try (var connection = ConnectionUtil.getConnection();
             var statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                var entity = new EmployeeEntity();
                entity.setId(resultSet.getLong(EMPLOYEE_ID));
                entity.setName(resultSet.getString(EMPLOYEE_NAME));
                entity.setSalary(resultSet.getBigDecimal(EMPLOYEE_SALARY));
                var birthdayInstant = resultSet.getTimestamp(EMPLOYEE_BIRTHDAY).toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                entity.setContacts(new ArrayList<>());
                do {
                    ContactEntity contact = new ContactEntity();
                    contact.setId(resultSet.getLong(CONTACT_ID));
                    contact.setDescription(resultSet.getString(CONTACT_DESCRIPTION));
                    contact.setType(resultSet.getString(CONTACT_TYPE));
                    entity.getContacts().add(contact);
                } while (resultSet.next());
                return Optional.of(entity);
            }
        } catch (SQLException ex) {
            logger.error("Error finding employee by ID: {}", id, ex);
            throw new DataAccessException("Error finding employee by ID", ex);
        }
        return Optional.empty();
    }

    /**
     * Sets the parameters of a prepared statement with employee data.
     *
     * @param statement The prepared statement to set parameters on
     * @param entity    The employee entity containing the data
     * @throws SQLException if a database access error occurs
     */
    private void setEmployeeParameters(java.sql.PreparedStatement statement, EmployeeEntity entity)
            throws SQLException {
        Objects.requireNonNull(statement, "Prepared statement cannot be null");
        validateEntity(entity);

        statement.setString(1, entity.getName());
        statement.setBigDecimal(2, entity.getSalary());
        statement.setTimestamp(3,
                Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(UTC).toLocalDateTime())
        );
    }

    /**
     * Validates that an employee entity is not null and has valid field values.
     *
     * @param entity The employee entity to validate
     * @throws IllegalArgumentException if the entity is null or has invalid field values
     */
    private void validateEntity(EmployeeEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employee entity cannot be null");
        }
        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        if (entity.getSalary() == null || entity.getSalary().signum() < 0) {
            throw new IllegalArgumentException("Employee salary must be a non-negative value");
        }
        if (entity.getBirthday() == null) {
            throw new IllegalArgumentException("Employee birthday cannot be null");
        }
    }

    private String formatOffsetDateTime(final OffsetDateTime dateTime) {
        var utcDatetime = dateTime.withOffsetSameInstant(UTC);
        return utcDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

/**
 * Custom exception class for data access layer exceptions.
 */
class DataAccessException extends RuntimeException {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
