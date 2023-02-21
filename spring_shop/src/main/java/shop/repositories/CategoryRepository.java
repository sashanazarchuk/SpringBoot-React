package shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
