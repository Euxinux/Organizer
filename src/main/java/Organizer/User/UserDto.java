package Organizer.User;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserDto{

    public UserDto(@NonNull String userLogin, @NonNull String userPassword, @NonNull String userEmail) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @NonNull
   // @Column(name = "user_login")
    private String userLogin;

    @NonNull
    private String userPassword;

    @NonNull
    private String userEmail;

    private boolean userEnabled;

}
