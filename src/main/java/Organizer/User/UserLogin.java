package Organizer.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserLogin{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserPassword userPassword;

    public String loginUser(UserDto userDto) throws JsonProcessingException{
        List<UserDto> byUserLoginOrEmail = userRepository.findUserByLoginOrEmail(userDto.getUserLogin());
        if(byUserLoginOrEmail.isEmpty())
            return "";
        else{
            String password1 = userDto.getUserPassword();
            String password2 = byUserLoginOrEmail.get(0).getUserPassword();
            boolean isEqual = userPassword.comparePassword(password1,password2);
            if (isEqual) {
                return objectMapper.writeValueAsString(byUserLoginOrEmail);
            }
            else{
                    return "";
                }
        }
    }


}
