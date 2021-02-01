package com.waflo.cooltimediaplattform.ui.component.search;

import com.vaadin.flow.spring.annotation.SpringComponent;

import java.util.List;
import java.util.Optional;

@SpringComponent
public interface ISearchCommand<T> {
    List<T> search(String value);
    Optional<? extends T> findByValue(String value);
    Optional<String> getResultURI(T result);
}
