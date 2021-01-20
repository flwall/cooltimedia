package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
