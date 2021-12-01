package Organizer.UserTests;

import Organizer.User.UserPassword;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class UserPasswordTests{

    @Test
    void it_should_compare_two_password(){
        //given
        String password = "Password123*";
        String passwordToCompare = "$2a$10$srz.vmCDy4hQzQWcFNH5COuqvHjYB0jbB1e9v.ulg/RwV05FPm.6C";
        UserPassword userPassword = new UserPassword();
        //when
        boolean isTheSame = userPassword.comparePassword(password,passwordToCompare);
        //then
        Assert.assertEquals(true,isTheSame);
    }
}
