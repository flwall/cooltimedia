package com.waflo.cooltimediaplattform.backend.repository;

import com.waflo.cooltimediaplattform.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
