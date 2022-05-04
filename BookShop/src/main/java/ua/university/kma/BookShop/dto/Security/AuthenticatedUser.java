package ua.university.kma.BookShop.dto.Security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class AuthenticatedUser extends User {

    public AuthenticatedUser(
            final String email,
            final String password,
            final List<? extends GrantedAuthority> authorities
    ) {
        super(email, password, authorities);
    }
}