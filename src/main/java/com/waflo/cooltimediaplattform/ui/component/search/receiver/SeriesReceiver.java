package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.waflo.cooltimediaplattform.backend.model.Series;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.SeriesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SeriesReceiver implements IReceiver<Series> {
    private final SeriesService seriesService;
    private final UserSession session;

    public SeriesReceiver(SeriesService seriesService, UserSession session) {
        this.seriesService = seriesService;
        this.session = session;
    }

    @Override
    public List<Series> search(String value) {
        return seriesService.findAllByUser(session.getUser().getId()).stream().filter(s -> s.getName().equalsIgnoreCase(value)).collect(Collectors.toList());
    }

    @Override
    public Optional<Series> findByValue(String value) {
        return search(value).stream().findFirst();
    }
}
