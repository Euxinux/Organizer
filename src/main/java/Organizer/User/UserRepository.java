package Organizer.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDto,Long> {
     Optional<UserDto> findByUserLogin(String userLogin);
     Optional<UserDto> findByUserEmail(String userEmail);
     Optional<UserDto> findByUserId(int id);

     @Query(value = "SELECT * FROM organizer.user WHERE user_login = ?1 OR user_email = ?1", nativeQuery = true)
     Optional<UserDto> findUserByLoginOrEmail(String loginOrEmail);
}
