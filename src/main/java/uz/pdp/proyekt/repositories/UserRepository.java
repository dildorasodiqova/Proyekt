package uz.pdp.proyekt.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.proyekt.entities.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {






}
