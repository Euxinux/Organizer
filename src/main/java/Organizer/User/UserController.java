package Organizer.User;

import Organizer.Registration.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController{

    @Autowired
    UserRegistration userRegistration;
    @Autowired
    UserLogin userLogin;
    @Autowired
    UserPassword userPassword;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid UserDto userDto, Errors errors){
            return userRegistration.registerUser(userDto,errors);
    }
    @GetMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserDto userDto){
        return userLogin.loginUser(userDto);
    }

    @GetMapping("/confirm")
    public ResponseEntity confirmEmail(@RequestParam String token){
            return userRegistration.confirmEmail(token);
    }

    @GetMapping("/test")
    public ResponseEntity testMethod(@RequestBody @Valid UserDto userDto, Errors errors){
        String password = userPassword.encodePassword(userDto.getUserPassword());
        return new ResponseEntity(userDto.getUserPassword() + password,HttpStatus.OK);
    }


}
