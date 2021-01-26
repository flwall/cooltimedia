package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.Rating;
import com.waflo.cooltimediaplattform.repository.RatingRepository;
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
