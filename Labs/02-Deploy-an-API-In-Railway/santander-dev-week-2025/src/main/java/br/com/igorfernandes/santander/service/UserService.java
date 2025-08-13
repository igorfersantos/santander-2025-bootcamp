package br.com.igorfernandes.santander.service;

import br.com.igorfernandes.santander.model.User;

public interface UserService {

    User findById(Long id);

    User create(User userToCreate);
}
