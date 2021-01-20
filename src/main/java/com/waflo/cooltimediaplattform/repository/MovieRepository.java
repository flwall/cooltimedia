package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
