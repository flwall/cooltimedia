package com.waflo.cooltimediaplattform.backend.repository;

import com.waflo.cooltimediaplattform.backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
