package com.waflo.cooltimediaplattform.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import org.springframework.security.access.annotation.Secured;

@Route(value = "account", layout = MainLayout.class)
@Secured("ROLE_USER")
public class AccountView extends VerticalLayout {

    public AccountView(){


    }




}
