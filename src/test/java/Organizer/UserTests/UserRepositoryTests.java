package Organizer.UserTests;

import Organizer.User.UserDto;
import Organizer.User.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class UserRepositoryTests{

    @Autowired
    private  UserRepository testRepository;

    @AfterEach
    void tearDown(){
        testRepository.deleteAll();
    }


     // Optional<UserDto> findByUserLogin(String userLogin);
   @Test
    void should_check_when_user_login_exist(){
        //given
        String login = "123456789";
        UserDto user = new UserDto(login,"RamPamPam01*","mail@gmail.com");
        testRepository.save(user);
        //when
        Optional<UserDto> expected = testRepository.findByUserLogin(login);
        //then
        Assert.assertEquals(expected.isPresent(),true);
    }
    @Test
    void should_check_when_user_login_does_not_exist(){
        //given
        String login = "123456789";
        //when
        Optional<UserDto> expected = testRepository.findByUserLogin(login);
        //then
        Assert.assertEquals(expected.isPresent(),false);
    }


     // Optional<UserDto> findByUserLogin(String userLogin);
    @Test
    void should_check_when_user_email_exist(){
        //given
        String email = "mail@gmail.com";
        UserDto user = new UserDto(email,"RamPamPam01*","mail@gmail.com");
        testRepository.save(user);
        //when
        Optional<UserDto> expected = testRepository.findByUserEmail(email);
        //then
        Assert.assertEquals(expected.isPresent(),true);
    }
    @Test
    void should_check_when_user_email_does_not_exist(){
        //given
        String mail = "mail@gmail.com";
        //when
        Optional<UserDto> expected = testRepository.findByUserEmail(mail);
        //then
        Assert.assertEquals(expected.isPresent(),false);
    }

    // Optional<UserDto> findUserByLoginOrEmail(String loginOrEmail);
    @Test
    void should_check_when_user_email_or_login_exist(){
        //given
        String emailOrLogin = "123456789";
        String emailOrLogin2 = "mail@gmail.com";
        UserDto user = new UserDto("123456789","RamPamPam01*","mail@gmail.com");
        testRepository.save(user);
        //when
        Optional<UserDto> expected = testRepository.findUserByLoginOrEmail(emailOrLogin);
        Optional<UserDto> expected2 = testRepository.findUserByLoginOrEmail(emailOrLogin);

        //then
        Assert.assertEquals(expected.isPresent(),true);
        Assert.assertEquals(expected2.isPresent(),true);
    }
    @Test
    void should_check_when_user_email_or_login_does_not_exist(){
        //given
        String mailOrLogin = "mail@gmail.com";
        //when
        Optional<UserDto> expected = testRepository.findByUserLogin(mailOrLogin);
        //then
        Assert.assertEquals(expected.isPresent(),false);
    }




}