package uz.pdp.proyekt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.proyekt.entities.AttachmentEntity;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, UUID> {
Optional<AttachmentEntity> findByName(String fileName);

}
