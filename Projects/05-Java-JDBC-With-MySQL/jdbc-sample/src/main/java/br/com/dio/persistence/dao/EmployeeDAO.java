package br.com.dio.persistence.dao;

import br.com.dio.persistence.entity.EmployeeEntity;

import java.util.List;

public class EmployeeDAO implements EntityDAO<EmployeeEntity> {

    /**
     * Inserts a new employee entity into the database.
     *
     * @param entity The employee entity to be inserted.
     */
    @Override
    public void insert(final EmployeeEntity entity) {
    }


    /**
     * Updates an existing employee entity in the database.
     *
     * @param entity The employee entity to be updated.
     */
    @Override
    public void update(final EmployeeEntity entity) {
    }


    /**
     * Deletes an employee entity from the database based on its ID.
     *
     * @param id The ID of the employee entity to be deleted.
     */
    @Override
    public void delete(final long id) {
    }


    /**
     * Returns a list of all employee entities in the database.
     *
     * @return A list of all employee entities in the database.
     */
    @Override
    public List<EmployeeEntity> findAll() {
        return null;
    }


    /**
     * Finds an employee entity by its ID in the database.
     *
     * @param id The ID of the employee entity to be found.
     * @return The employee entity with the given ID, or null if no such entity exists.
     */
    @Override
    public EmployeeEntity findById(final long id) {
        return null;
    }
}
