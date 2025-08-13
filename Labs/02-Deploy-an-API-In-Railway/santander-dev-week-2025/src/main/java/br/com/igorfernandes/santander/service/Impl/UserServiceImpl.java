package br.com.igorfernandes.santander.service.Impl;

import br.com.igorfernandes.santander.model.User;
import br.com.igorfernandes.santander.model.UserRepository;
import br.com.igorfernandes.santander.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(final User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber()))
            throw new IllegalArgumentException("This account number already exists");
        return userRepository.save(userToCreate);
    }
}
