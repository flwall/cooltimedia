package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
