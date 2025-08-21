package br.com.dio.persistence.dao.contact;

import br.com.dio.persistence.ConnectionUtil;
import br.com.dio.persistence.dao.EntityDAO;
import br.com.dio.persistence.dao.Insertable;
import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

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
public class ContactDAO implements Insertable<ContactEntity> {
    private static final Logger logger = LoggerFactory.getLogger(ContactDAO.class);
    private static final String EMPLOYEE_ID = "id";
    private static final String EMPLOYEE_NAME = "name";
    private static final String EMPLOYEE_SALARY = "salary";
    private static final String EMPLOYEE_BIRTHDAY = "birthday";

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the provided entity is null
     * @throws DataAccessException      if a database access error occurs
     */
    @Override
    public void insert(final ContactEntity entity) {
        final String sql = "INSERT INTO contacts (description, type, employee_id) VALUES (?, ?, ?)";

        try (var connection = ConnectionUtil.getConnection();
             var statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getDescription());
            statement.setString(2, entity.getType());
            statement.setLong(3, entity.getEmployee().getId());

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
    public void insertMany(final List<ContactEntity> entities) {

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
}
