package ua.university.kma.BookShop.Config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import ua.university.kma.BookShop.Service.CustomUserDetailsService;
import ua.university.kma.BookShop.db.UserRepository;
//import ua.university.kma.BookShop.dto.model.Role;

@EnableWebSecurity
@AllArgsConstructor
public class MyWebConfig extends WebSecurityConfigurerAdapter {

    UserRepository userRepository;

    @Override
    protected UserDetailsService userDetailsService() {return new CustomUserDetailsService(userRepository);}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/profile").authenticated()
                .antMatchers("/").permitAll()
                .antMatchers("/register").anonymous()
                .and()
                .formLogin().defaultSuccessUrl("/").permitAll()
                .and().logout()
                .logoutSuccessUrl("/").permitAll();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
