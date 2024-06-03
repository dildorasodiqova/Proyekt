package uz.pdp.proyekt.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.proyekt.entities.CategoryEntity;

import java.util.Optional;
import java.util.UUID;


public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByName(String name);
    Page<CategoryEntity> findAllByIsActiveTrue(PageRequest pageRequest);

}
