package test.muruna.com.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.muruna.com.exceptions.UserAlreadyExistsException;
import test.muruna.com.repositories.UserRepository;
import test.muruna.com.repositories.entities.Users;
import test.muruna.com.services.UserService;
import test.muruna.com.utils.GenerateToken;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenerateToken generateToken;


    /**
     * Metodo que persiste un usuario
     * en la BD , y ademas valida si el email
     * ya existe anteriormente.
     * @param user
     * @return
     */
    public Users createUser(Users user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("El correo existe anteriormente");
        }

        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(GenerateToken.getToken());
        user.setActive(true);

        return userRepository.save(user);
    }
}
