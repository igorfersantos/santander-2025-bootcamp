package br.com.dio.persistence.dao;

import java.util.List;


/**
 * Interface abstraction for DAOs that we may end up using
 *
 * @param <T> The Entity Class that the DAO will operate upon.
 */
interface EntityDAO<T> {
    /**
     * Inserts a new entity into the database.
     *
     * @param entity The entity to be inserted.
     */
    void insert(final T entity);

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity to be updated.
     */
    void update(final T entity);

    /**
     * Deletes an entity from the database based on its ID.
     *
     * @param id The ID of the entity to be deleted.
     */
    void delete(final long id);

    /**
     * Returns a list of all entities in the database.
     *
     * @return A list of all entities in the database.
     */
    List<T> findAll();

    /**
     * Finds an entity by its ID in the database.
     *
     * @param id The ID of the entity to be found.
     * @return The entity with the given ID, or null if no such entity exists.
     */
    T findById(final long id);
}
