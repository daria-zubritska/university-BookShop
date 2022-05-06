package ua.university.kma.BookShop.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.university.kma.BookShop.db.UserRepository;
//import ua.university.kma.BookShop.dto.model.RoleEntity;
import ua.university.kma.BookShop.dto.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(name).orElseThrow(() -> new RuntimeException("No user found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(String.valueOf(getAuthorities().add(new SimpleGrantedAuthority("USER"))))
                .build();
    }


    private List<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>();
    }

}
