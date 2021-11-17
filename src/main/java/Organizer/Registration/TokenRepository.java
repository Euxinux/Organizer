package Organizer.Registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken,Long>{

    Optional<VerificationToken> findByToken(String token);
}
