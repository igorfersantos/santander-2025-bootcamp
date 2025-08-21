package br.com.dio.persistence.dao;

import java.util.List;
import java.util.Optional;


/**
 * Interface abstraction for DAOs that we may end up using
 *
 * @param <T> The Entity Class that the DAO will operate upon.
 */
public interface EntityDAO<T> extends Findable<T>, Insertable<T>, Updatable<T>, Deletable {

}
