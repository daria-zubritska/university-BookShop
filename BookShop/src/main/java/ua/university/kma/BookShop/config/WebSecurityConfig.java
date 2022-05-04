package ua.university.kma.BookShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


import lombok.RequiredArgsConstructor;
import ua.university.kma.BookShop.db.UserRepository;
import ua.university.kma.BookShop.db.UserService;
import ua.university.kma.BookShop.dto.model.PermissionEntity;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin", "/admin/**").hasAuthority(PermissionEntity.Permission.ADMIN.name())
                .antMatchers("/catalog").hasAuthority(PermissionEntity.Permission.USER.name())
                .antMatchers("/profile").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserService();
    }
}