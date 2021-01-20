package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
