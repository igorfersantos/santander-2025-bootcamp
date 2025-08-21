package br.com.dio.persistence.dao;

import java.util.List;
import java.util.Optional;

public interface Findable<T> {
    /**
     * Returns a list of all entities in the database.
     *
     * @return A list of all entities in the database.
     */
    List<T> findAll();

    /**
     * Finds an {@link br.com.dio.persistence.entity.EmployeeEntity} entity by its ID in the database and returns it
     * wrapped in an Optional.
     *
     * @param id The ID of the entity to be found.
     * @return An Optional containing the entity with the given ID, or an empty Optional if no such entity exists.
     */
    Optional<T> findById(long id);
}
