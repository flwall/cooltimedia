package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.waflo.cooltimediaplattform.backend.model.Series;
import com.waflo.cooltimediaplattform.backend.service.SeriesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SeriesReceiver implements IReceiver<Series>{
    private SeriesService seriesService;

    public SeriesReceiver(SeriesService seriesService){
        this.seriesService = seriesService;
    }
    @Override
    public List<Series> search(String value) {
        return seriesService.findAll().stream().filter(s->s.getName().equalsIgnoreCase(value)).collect(Collectors.toList());
    }

    @Override
    public Optional<Series> findByValue(String value) {
        return search(value).stream().findFirst();
    }
}
