package uz.pdp.proyekt.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.proyekt.entities.UserPassword;

import java.util.Optional;
import java.util.UUID;

@RestController
public interface PasswordRepository extends JpaRepository<UserPassword, UUID> {
    @Query(nativeQuery = true, value = """
   SELECT up.* FROM passwords up
                       INNER JOIN users u ON up.user_id = u.id
   WHERE up.code = :password AND u.id = :userId
""")
    Optional<UserPassword> getUserPasswordById(UUID userId, String password);
}
