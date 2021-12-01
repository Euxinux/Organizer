package Organizer.User;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserDto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @NonNull
    @NotBlank(message = "Login can not be blank.")
    @Size(min = 6, max = 20, message = "User login must be between {min} and {max} chars.")
    private String userLogin;

    @NonNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$",
             message = "Password is incorrect")
    private String userPassword;

    @NonNull
    @Email(message = "Email should be properly formatted.")
    private String userEmail;

    private boolean userEnabled;

}
