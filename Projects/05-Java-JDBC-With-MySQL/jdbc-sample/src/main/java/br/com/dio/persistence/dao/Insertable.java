package br.com.dio.persistence.dao;

import java.util.List;

public interface Insertable<T> {
    /**
     * Inserts a new entity into the database.
     *
     * @param entity The entity to be inserted.
     */
    void insert(T entity);

    /**
     * Inserts a list of new entities into the database. This method is more efficient
     * than inserting each entity individually with {@link #insert(Object)}.
     *
     * @param entities The entities to be inserted.
     */
    void insertMany(final List<T> entities);
}
