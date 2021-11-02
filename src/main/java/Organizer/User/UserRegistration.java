package Organizer.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class UserRegistration {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPassword userPassword;

    /* Method check that login/email is existing in DB,
       if login/email never used before, create new account,
       hash password with BCrypt
     */
    public HttpStatus registerUser (UserDto userDto) {

        //check login
        Optional<UserDto> newUser = userRepository.findByUserLogin(userDto.getUserLogin());
        if(newUser.isPresent()){
            return HttpStatus.NOT_ACCEPTABLE;
        }
        //check emial
        newUser = userRepository.findByUserEmail(userDto.getUserEmail());
        if(newUser.isPresent()){
            return HttpStatus.NOT_ACCEPTABLE;
        }
        // encode password and save user
        String encryptedPassword = userPassword.encodePassword(userDto.getUserPassword());
        userDto.setUserPassword(encryptedPassword);
        userRepository.save(userDto);
        return HttpStatus.CREATED;
    }

}
