package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.waflo.cooltimediaplattform.backend.model.Rating;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RatingComponent extends VerticalLayout {

    public Rating getBean() {
        return bean;
    }

    private final VerticalLayout comments;
    private final Rating bean;

    public RatingComponent(Collection<Rating> ratings, ComponentEventListener<SaveEvent> listener) {
        var stars = new RatingStars(5);
        var text = new TextArea("Kommentar", "Kommentar");
        var img = new Image("/imgs/send.png", "Kommentar senden");
        img.setWidth("20px");
        var btn = new Button(img);

        var binder = new BeanValidationBinder<>(Rating.class);
        bean = new Rating();
        binder.setBean(bean);
        binder.bind(stars, Rating::getRating, Rating::setRating);
        binder.bind(text, Rating::getComment, Rating::setComment);

        var self = this;

        btn.addClickListener((ev) -> {
            try {
                binder.writeBean(this.bean);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            bean.setCreatedAt(LocalDateTime.now());
            var reg = getEventBus().addListener(SaveEvent.class, listener);
            fireEvent(new SaveEvent(self, false));
            reg.remove();   //listen for duration of click
        });


        var form = new FormLayout(new VerticalLayout(stars, new HorizontalLayout(text, btn)));

        comments = new VerticalLayout();
        ratings = ratings.stream().sorted(Comparator.comparing(Rating::getCreatedAt)).collect(Collectors.toList());
        for (Rating rating : ratings) {
            addComment(rating);
        }

        add(form, comments);
    }

    public void addComment(Rating rating) {
        if (comments != null) {
            var st = new RatingStars(5);
            st.setRating(rating.getRating());
            st.setReadOnly(true);
            var by = new Paragraph("von " + rating.getCreator().getUsername());
            by.getStyle().set("font-weight", "bold");
            comments.addComponentAsFirst(new Div(by, st, new Text(rating.getComment())));
        }
    }
}
