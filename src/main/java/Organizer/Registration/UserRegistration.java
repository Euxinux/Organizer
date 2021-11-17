package Organizer.Registration;

import Organizer.Email.EmailService;
import Organizer.Email.EmailTemplate;
import Organizer.User.UserDto;
import Organizer.User.UserPassword;
import Organizer.User.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.HibernateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Getter
@Setter
@Transactional
public class UserRegistration {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPassword userPassword;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    EmailTemplate emailTemplate;


    /* Method check that login/email is existing in DB,
       if login/email never used before, create new account,
       hash password with BCrypt
     */
    public ResponseEntity registerUser (UserDto userDto, Errors errors){

        if (errors.hasErrors()) {
            return new ResponseEntity(errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

            //check login
            Optional<UserDto> newUser = userRepository.findByUserLogin(userDto.getUserLogin());
            if (newUser.isPresent()) {
                return new ResponseEntity("Login is exist", HttpStatus.NOT_ACCEPTABLE);
            }
            //check email
            newUser = userRepository.findByUserEmail(userDto.getUserEmail());
            if (newUser.isPresent()) {
                return new ResponseEntity("E-mail is exist", HttpStatus.NOT_ACCEPTABLE);
            }
            // encode password and save user
            String encryptedPassword = userPassword.encodePassword(userDto.getUserPassword());
            userDto.setUserPassword(encryptedPassword);
            userRepository.save(userDto);

            // create and save verification token
            String token = UUID.randomUUID().toString();
            LocalDateTime creationDate = LocalDateTime.now();
            Optional<UserDto> savedUser = userRepository.findByUserEmail(userDto.getUserEmail());
            VerificationToken verificationToken = new VerificationToken(savedUser.get(), token,
                    creationDate, creationDate.plusMinutes(60));
            tokenRepository.save(verificationToken);

            // send confirmation e-mail
            emailService.sendEmail(savedUser.get().getUserEmail(),
                    emailTemplate.buildEmailRegistration(savedUser.get().
                            getUserLogin(), "localhost:8080/confirm?token=" + token));

            return new ResponseEntity("Registration successfully", HttpStatus.OK);
    }

    /* Method responsible for confirm e-mail,
       check expiration time
       verification token exist in database. */
    public ResponseEntity confirmEmail(String token){

        //check if the token is in database
        Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
        if(verificationToken.isEmpty())
            return new ResponseEntity("Verification Token doesn't exist.",HttpStatus.BAD_REQUEST);
        LocalDateTime currentTime = LocalDateTime.now();

        //actual time isBefore than expiration time
        if (currentTime.isAfter(verificationToken.get().getExpirationDate())) {
            System.out.println("xxxx");
            return new ResponseEntity("Confirmation time is up", HttpStatus.NOT_ACCEPTABLE);
        }
        // save change is user and verificationToken database
        verificationToken.get().setConfirmationDate(currentTime);
        tokenRepository.save(verificationToken.get());
        Optional<UserDto> userById = userRepository.findByUserId(verificationToken.get().getUserDto().getUserId());
        userById.get().setUserEnabled(true);
        userRepository.save(userById.get());
        return new ResponseEntity("Your account confirmed successfully",HttpStatus.OK);
    }

}
