package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.Series;
import com.waflo.cooltimediaplattform.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
}
