package ua.university.kma.BookShop.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class LogoutSuccessHandlerAdapter implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
