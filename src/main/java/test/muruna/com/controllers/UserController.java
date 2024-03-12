package test.muruna.com.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.muruna.com.exceptions.UserAlreadyExistsException;
import test.muruna.com.repositories.entities.Users;
import test.muruna.com.services.UserService;
import test.muruna.com.utils.EmailValidator;
import test.muruna.com.utils.PasswordValidator;

import java.util.Collections;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailValidator emailValidator;

    /**
     * Metodo que crea un usuario dado un body
     * de tipo JSON
     * @param user
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        try {
            log.info("Validating email");
            if (!EmailValidator.isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "Formato de correo electrónico no válido"));
            }
            log.info("Validating password");
            if (!PasswordValidator.isValidPassword(user.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "Password no cumple con el formato especificado"));
            }

            log.info("Call user.createUser()");
            Users createdUser = userService.createUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }
}
