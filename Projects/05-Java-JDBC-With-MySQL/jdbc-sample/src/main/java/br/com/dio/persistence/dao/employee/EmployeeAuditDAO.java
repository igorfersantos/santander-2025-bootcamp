package br.com.dio.persistence.dao.employee;

import br.com.dio.persistence.ConnectionUtil;
import br.com.dio.persistence.dao.Findable;
import br.com.dio.persistence.entity.EmployeeAuditEntity;
import br.com.dio.persistence.entity.OperationEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.dio.persistence.DateUtil.getDateTimeOrNull;


public class EmployeeAuditDAO implements Findable<EmployeeAuditEntity> {
    private final String TABLE_NAME = "view_employee_audit";

    /**
     * Returns a list of all employee audit entities in the database.
     *
     * @return A list of all employee audit entities in the database.
     */
    @Override
    public List<EmployeeAuditEntity> findAll() {
        List<EmployeeAuditEntity> employeeAuditEntities = new ArrayList<>();
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            final String sql = "SELECT * FROM %s";
            final ResultSet resultSet = statement.executeQuery(sql.formatted(TABLE_NAME));
            while (resultSet.next()) {
                employeeAuditEntities.add(new EmployeeAuditEntity(
                        resultSet.getLong("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("old_name"),
                        resultSet.getBigDecimal("salary"),
                        resultSet.getBigDecimal("old_salary"),
                        getDateTimeOrNull(resultSet, "birthday"),
                        getDateTimeOrNull(resultSet, "old_birthday"),
                        OperationEnum.getByDbOperation(resultSet.getString("operation"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        return employeeAuditEntities;
    }

    /**
     * Finds an employee audit entity by its ID in the database.
     *
     * @param id The ID of the employee audit entity to be found.
     * @return An Optional containing the employee audit entity with the given ID,
     * or an empty Optional if no such entity exists.
     */
    @Override
    public Optional<EmployeeAuditEntity> findById(final long id) {
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            final String sql = "SELECT * FROM %s WHERE employee_id = %s";
            final ResultSet resultSet = statement.executeQuery(sql.formatted(TABLE_NAME, id));
            if (resultSet.next()) {
                return Optional.of(new EmployeeAuditEntity(
                        resultSet.getLong("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("old_name"),
                        resultSet.getBigDecimal("salary"),
                        resultSet.getBigDecimal("old_salary"),
                        getDateTimeOrNull(resultSet, "birthday"),
                        getDateTimeOrNull(resultSet, "old_birthday"),
                        OperationEnum.getByDbOperation(resultSet.getString("operation"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }


}
