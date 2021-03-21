package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.model.Role;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoleFilter implements Filter {

    private final UserSession userSession;

    public RoleFilter(UserSession userSession){
        this.userSession=userSession;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!((HttpServletRequest)request).getServletPath().startsWith("/admin")){
            chain.doFilter(request, response);
            return;
        }
        if(SecurityContextHolder.getContext().getAuthentication()!=null&&userSession.getUser().getRoles().contains(Role.ROLE_ADMIN)){
            chain.doFilter(request, response);
            return;
        }
        var resp=(HttpServletResponse)response;
        resp.sendError(403, "Not Permitted");
    }
}
