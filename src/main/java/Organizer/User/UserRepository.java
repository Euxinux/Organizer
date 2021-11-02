package Organizer.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDto,Long> {
     Optional<UserDto> findByUserLogin(String userLogin);
     Optional<UserDto> findByUserEmail(String userEmail);

     @Query(value = "SELECT * FROM organizer.user WHERE user_login = ?1 OR user_email = ?1", nativeQuery = true)
     List<UserDto> findUserByLoginOrEmail(String loginOrEmail);
}
