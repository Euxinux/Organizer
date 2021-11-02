package Organizer.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControler{

    @Autowired
    UserRegistration userRegistration;
    @Autowired
    UserLogin userLogin;
    @Autowired
    private UserPassword userPassword;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDto userDto) throws JsonProcessingException{
        HttpStatus httpStatus = userRegistration.registerUser(userDto);
        return ResponseEntity.ok(httpStatus);
    }

    @GetMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserDto userDto) throws JsonProcessingException{
        String objectMapper = userLogin.loginUser(userDto);
        return ResponseEntity.ok(objectMapper);
    }
    @GetMapping("/test")
    public void testMethod(@RequestBody UserDto userDto) throws JsonProcessingException{
        System.out.println(userPassword.encodePassword("polska01"));
    }

}
