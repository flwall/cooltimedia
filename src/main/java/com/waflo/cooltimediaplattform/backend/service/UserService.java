package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.User;
import com.waflo.cooltimediaplattform.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByOauthId(String oauth_id) {
        return userRepository.findAll().stream().filter(s -> s.getOauth_id().equals(oauth_id)).findFirst();
    }

    public User save(User u) {
        return userRepository.save(u);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findAll().stream().filter(u->u.getUsername().equals(username)).findFirst();
    }
}
