package ua.university.kma.BookShop.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.university.kma.BookShop.dto.Security.AuthenticatedUser;
import ua.university.kma.BookShop.dto.model.PermissionEntity;
import ua.university.kma.BookShop.dto.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + email));

        return new AuthenticatedUser(
                email,
                user.getPassword(),
                mapAuthorities(user.getPermissions())
        );
    }

    private static List<GrantedAuthority> mapAuthorities(final List<PermissionEntity> permissions) {
        return permissions.stream()
                .map(PermissionEntity::getPermission)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }

}
