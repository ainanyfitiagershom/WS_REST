package mg.restaurant.restapi.repository;

import mg.restaurant.restapi.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(Long restaurantId);
    List<Menu> findByPriceBetween(double min, double max);
    List<Menu> findByNameContainingIgnoreCase(String name);
}
