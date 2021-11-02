package Organizer.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class UserPassword{

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    //encoding password by BCrypt.
    public String encodePassword(String password){
        String hashPassword = bCryptPasswordEncoder.encode(password);
        return hashPassword;
    }
    // compare password with hash password in DB.
    public boolean comparePassword(String passwordToCompare, String passwordCompared){
        boolean isEqual = bCryptPasswordEncoder.matches(passwordToCompare,passwordCompared);
        return isEqual;
    }
}
