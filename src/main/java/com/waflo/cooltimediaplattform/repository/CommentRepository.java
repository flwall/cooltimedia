package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
