package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Season;
import com.waflo.cooltimediaplattform.backend.repository.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository categoryRepository) {
        this.seasonRepository = categoryRepository;
    }


    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    public Optional<Season> findById(long id) {
        return seasonRepository.findById(id);
    }
}
