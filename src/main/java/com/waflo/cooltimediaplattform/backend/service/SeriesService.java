package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.model.Series;
import com.waflo.cooltimediaplattform.backend.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }


    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    public Optional<Series> findById(long id) {
        return seriesRepository.findById(id);
    }

    public Series save(Series entity) {
        return seriesRepository.save(entity);
    }
    public List<Series> findAllByUser(long userId) {
        return seriesRepository.findAll().stream().filter(a -> a.getOwner().stream().anyMatch(o -> o.getId() == userId)).collect(Collectors.toList());
    }
}
