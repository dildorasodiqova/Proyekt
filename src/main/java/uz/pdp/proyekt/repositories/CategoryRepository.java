package uz.pdp.proyekt.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.proyekt.entities.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByName(String name);
    Page<CategoryEntity> getCategoryEntitiesByParentId_Id(UUID parentId, Pageable pageable);

    Page<CategoryEntity> findAllByIsActiveTrue(Pageable pageable);


    @Query("select  count(c)>0 from  CategoryEntity  c where c.id != ?1 and c.name = ?2")
    Boolean existsAllBy(UUID categoryId, String name);

    @Query("select c from CategoryEntity c where c.parentId is null")
    List<CategoryEntity> getFirstCategory();



}
