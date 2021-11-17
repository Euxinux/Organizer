package Organizer.Registration;

import Organizer.User.UserDto;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class VerificationToken{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserDto userDto;
    @NonNull
    private String token;
    @NonNull
    private LocalDateTime creationDate;
    @NonNull
    private LocalDateTime expirationDate;
    private LocalDateTime confirmationDate;


}
