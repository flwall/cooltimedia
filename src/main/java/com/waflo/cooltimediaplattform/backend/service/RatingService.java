package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Rating;
import com.waflo.cooltimediaplattform.backend.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository categoryRepository) {
        this.ratingRepository = categoryRepository;
    }


    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> findById(long id) {
        return ratingRepository.findById(id);
    }
}
