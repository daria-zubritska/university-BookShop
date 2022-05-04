package ua.university.kma.BookShop.config;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.Value;
import ua.university.kma.BookShop.Service.JwtTokenGenerator;
import ua.university.kma.BookShop.dto.Security.AuthenticatedUser;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final JwtTokenGenerator jwtTokenGenerator;

    CustomLoginFilter(
            final AuthenticationManager authenticationManager,
            final ObjectMapper objectMapper,
            final JwtTokenGenerator jwtTokenGenerator
    ) {
        this.objectMapper = objectMapper;
        this.jwtTokenGenerator = jwtTokenGenerator;
        setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final UserCredentials userCredentials = objectMapper.readValue(request.getInputStream(), UserCredentials.class);

        final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword(), List.of());

        return getAuthenticationManager().authenticate(authToken);
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication auth) {

        SecurityContextHolder.getContext().setAuthentication(auth);
        final AuthenticatedUser authenticatedUser = (AuthenticatedUser) auth.getPrincipal();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(authenticatedUser));
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtTokenGenerator.generateToken(authenticatedUser));
    }

    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(
            final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed
    ) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Value
    private static class UserCredentials {
        private final String email;
        private final String password;
    }

}
