package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.waflo.cooltimediaplattform.security.UserSession;
import com.waflo.cooltimediaplattform.ui.component.AbstractForm;
import com.waflo.cooltimediaplattform.ui.events.CancelEvent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;

import java.util.List;

public abstract class AbstractEntitiesView<T> extends VerticalLayout {
    protected List<T> entities;
    protected AbstractForm<T> form;

    public AbstractEntitiesView(AbstractForm<T> form) {
        this.form = form;

        form.addListener(SaveEvent.class, this::saveEntity);
        form.addListener(CancelEvent.class, s -> closeEditor());

    }


    protected void saveEntity(SaveEvent t) {
        updateList();
        closeEditor();

    }

    private void updateList() {
        this.initList();
    }

    protected abstract void initList();


    public void openEditor(T entity) {
        form.setEntity(entity);
        form.setVisible(true);
        form.addClassName("editing");
    }


    protected void closeEditor() {
        form.setEntity(null);
        form.setVisible(false);
        removeClassName("editing");
    }

}
