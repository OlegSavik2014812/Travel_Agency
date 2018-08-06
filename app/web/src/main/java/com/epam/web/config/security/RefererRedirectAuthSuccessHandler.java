package com.epam.web.config.security;

import com.epam.core.entity.User;
import com.epam.core.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * The type Referer redirect auth success handler.
 */
@Component
public class RefererRedirectAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger LOGGER = LogManager.getLogger(RefererRedirectAuthSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final UserService service;

    @Autowired
    public RefererRedirectAuthSuccessHandler(UserService service) {
        this.service = service;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        final HttpSession session = request.getSession();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User user = service.getUserByLogin(principal.getUsername());
        session.setAttribute("user", user);
        if (response.isCommitted()) {
            LOGGER.debug("Response has already been committed.");
            return;
        }
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
