package mg.restaurant.restapi.repository;

import mg.restaurant.restapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
