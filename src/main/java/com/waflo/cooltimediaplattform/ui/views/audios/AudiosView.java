package com.waflo.cooltimediaplattform.ui.views.audios;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import org.springframework.security.access.annotation.Secured;

@Secured("ROLE_USER")
@PageTitle("Audios | CTM")
@Route(value = "audios", layout = MainLayout.class)
public class AudiosView extends VerticalLayout {
}
