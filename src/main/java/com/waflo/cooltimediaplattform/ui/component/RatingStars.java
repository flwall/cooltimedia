package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedList;
import java.util.List;

@CssImport("./rating-stars.css")
public class RatingStars extends HorizontalLayout implements HasValue<HasValue.ValueChangeEvent<Integer>, Integer> {

    private int maxRating;
    private int rating;
    private boolean isReadonly;

    public RatingStars(int maxRating) {
        this.maxRating = maxRating;
        this.init();
    }

    private void init() {
        var div = new Div();
        div.setClassName("container");
        var d2 = new Div();
        d2.setClassName("rating");

        for (int i = maxRating; i > 0; i--) {
            var inp = new Input();
            inp.setType("radio");
            inp.setId("rating-" + i);
            if (i == rating)
                inp.getElement().setAttribute("checked", "true");

            if (this.isReadonly)
                inp.setEnabled(false);

            final var x = i;
            inp.addValueChangeListener((ev) -> onRatingChange(x));
            var l = new Label();
            l.setFor(inp);

            d2.add(inp, l);

        }
        div.add(d2);

        add(div);
    }


    public void onRatingChange(int rat) {
        for (ValueChangeListener<? super ValueChangeEvent<Integer>> valueChangeListener : toNotify) {
            var self = this;
            var val = new ValueChangeEvent<Integer>() {
                @Override
                public HasValue<?, Integer> getHasValue() {
                    return self;
                }

                @Override
                public boolean isFromClient() {
                    return false;
                }

                @Override
                public Integer getOldValue() {
                    return self.rating;
                }

                @Override
                public Integer getValue() {
                    return rat;
                }
            };
            valueChangeListener.valueChanged(val);
        }
        this.rating = rat;
    }

    private final List<ValueChangeListener<? super ValueChangeEvent<Integer>>> toNotify = new LinkedList<>();

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.onRatingChange(rating);
        this.setValue(rating);
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
        this.removeAll();
        this.init();
    }

    @Override
    public void setValue(Integer value) {
        if (value >= 0 && value <= maxRating) {
            this.rating = value;
            this.removeAll();
            this.init();
        }
    }

    @Override
    public Integer getValue() {
        return rating;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<Integer>> listener) {
        toNotify.add(listener);

        return () -> toNotify.remove(listener);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.isReadonly = readOnly;
        this.removeAll();
        this.init();

    }

    @Override
    public boolean isReadOnly() {
        return this.isReadonly;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {

    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }
}
