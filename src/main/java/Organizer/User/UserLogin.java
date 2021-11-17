package Organizer.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserLogin{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPassword userPassword;

    public ResponseEntity loginUser(UserDto userDto){
        Optional<UserDto> byUserDtoLoginOrEmail = userRepository.findUserByLoginOrEmail(userDto.getUserLogin());
        if(byUserDtoLoginOrEmail.isEmpty())
            return new ResponseEntity("User doesn't exist", HttpStatus.NOT_FOUND);
        else{
            String passwordFromRequest = userDto.getUserPassword();
            String passwordFromDB = byUserDtoLoginOrEmail.get().getUserPassword();

            // compare password by BCrypt
            boolean isEqual = userPassword.comparePassword(passwordFromRequest,passwordFromDB);
            if (isEqual){
                return new ResponseEntity("Login successful",HttpStatus.OK);
            }
            else{
                return new ResponseEntity("Login or password is incorrect",HttpStatus.UNAUTHORIZED);
                }
        }
    }
}
