package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.User;
import com.waflo.cooltimediaplattform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository categoryRepository;

    public UserService(UserRepository userRepository) {
        this.categoryRepository = userRepository;
    }


    public List<User> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return categoryRepository.findById(id);
    }
}
