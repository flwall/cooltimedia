package com.waflo.cooltimediaplattform.security;

import com.waflo.cooltimediaplattform.model.User;
import com.waflo.cooltimediaplattform.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class UserSession implements Serializable {


    private final UserService userService;

    public UserSession(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal)) {
            LoggerFactory.getLogger(getClass().getName()).error(authentication.getPrincipal().toString());
            return new User();
        }
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        var u = userService.findByOauthId(principal.getName());
        if (u.isPresent()) return u.get();


        var newUser = new User(principal.getName(), principal.getAttribute("given_name"), principal.getAttribute("email"));
        return userService.save(newUser);
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

}