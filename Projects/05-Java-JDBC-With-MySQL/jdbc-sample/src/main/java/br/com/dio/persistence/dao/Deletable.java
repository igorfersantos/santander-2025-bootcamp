package br.com.dio.persistence.dao;

public interface Deletable {
    /**
     * Deletes an entity from the database based on its ID.
     *
     * @param id The ID of the entity to be deleted.
     */
    void delete(long id);
}
