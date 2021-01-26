package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.Season;
import com.waflo.cooltimediaplattform.repository.SeasonRepository;
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
