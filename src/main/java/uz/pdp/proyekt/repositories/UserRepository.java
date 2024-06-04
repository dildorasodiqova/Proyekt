package uz.pdp.proyekt.repositories;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.proyekt.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Page<UserEntity> findAllByIsActiveTrue(Pageable pageable);
    Optional<UserEntity> findAllByEmailOrUsername(String email, String username);

    Optional<UserEntity> findByEmail(String email);



}
