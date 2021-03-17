package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

@CssImport("./rating-stars.css")
public class RatingStars extends HorizontalLayout implements HasValue<HasValue.ValueChangeEvent<Integer>, Integer> {

    private int maxRating;
    private int rating;

    public RatingStars(int maxRating) {
        this.maxRating = maxRating;
        this.init();
    }

    private void init() {
        var div=new Div();
        div.setClassName("container");
        var d2=new Div();
        d2.setClassName("rating");

        for(int i=maxRating;i>0;i--){
            var inp=new Input();
            inp.setType("radio");
            inp.setId("rating-"+i);

            final var x=i;
            inp.addValueChangeListener((ev)->onRatingChange(x));
            var l=new Label();
            l.setFor(inp);

            d2.add(inp, l);

        }
        div.add(d2);

        add(div);
    }




    public void onRatingChange(int rat) {

        System.out.println("RATING DID CHANGE");
        for (ValueChangeListener<? super ValueChangeEvent<Integer>> valueChangeListener : toNotify) {
            var self=this;
            var val= new ValueChangeEvent<Integer>() {
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
        this.rating=rat;
    }

    private final List<ValueChangeListener<? super ValueChangeEvent<Integer>>> toNotify = new LinkedList<>();

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.onRatingChange(rating);
        this.rating = rating;
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
        if (value >= 0 && value <= maxRating)
            this.rating = value;
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

    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {

    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }
}
