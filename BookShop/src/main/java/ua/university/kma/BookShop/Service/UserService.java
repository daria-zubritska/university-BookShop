package ua.university.kma.BookShop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.university.kma.BookShop.db.UserRepository;
//import ua.university.kma.BookShop.dto.model.Role;
//import ua.university.kma.BookShop.dto.model.RoleEntity;
import ua.university.kma.BookShop.dto.model.User;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service("userService")
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Transactional
    public User createUser(String username, String hexedPsw){

        return userRepository.save(User.builder()
                .username(username)
                .password(hexedPsw)
                .build());
    }

    public void save(User user){
        userRepository.save(user);
    }

}
