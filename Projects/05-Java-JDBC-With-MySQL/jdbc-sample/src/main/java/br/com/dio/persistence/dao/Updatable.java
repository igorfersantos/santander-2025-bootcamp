package br.com.dio.persistence.dao;

public interface Updatable<T> {
    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity to be updated.
     */
    void update(T entity);
}
