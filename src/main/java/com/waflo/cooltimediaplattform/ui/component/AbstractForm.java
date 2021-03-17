package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public abstract class AbstractForm<R> extends FormLayout {

    protected Button save = new Button("Speichern"), close = new Button("Abbrechen");

    protected Binder<R> binder;
    protected R entity;

    public R getEntity() {
        return entity;
    }

    public void setEntity(R entity) {
        this.entity = entity;
        binder.readBean(entity);
    }

    protected Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(this::onSaveClick);
        close.addClickListener(this::onCancelClick);

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }


    protected abstract void onSaveClick(ClickEvent<Button> buttonClickEvent);

    protected abstract void onCancelClick(ClickEvent<Button> buttonClickEvent);


    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
