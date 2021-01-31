package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Episode;
import com.waflo.cooltimediaplattform.backend.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }


    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public Optional<Episode> findById(long id) {
        return episodeRepository.findById(id);
    }
}
