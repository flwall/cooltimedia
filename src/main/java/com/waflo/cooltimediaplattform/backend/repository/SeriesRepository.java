package com.waflo.cooltimediaplattform.backend.repository;

import com.waflo.cooltimediaplattform.backend.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
