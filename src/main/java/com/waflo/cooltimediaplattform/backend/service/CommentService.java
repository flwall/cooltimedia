package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Comment;
import com.waflo.cooltimediaplattform.backend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepository = commentRepo;
    }


    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }
}
