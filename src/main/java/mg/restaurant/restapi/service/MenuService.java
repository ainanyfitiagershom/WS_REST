package mg.restaurant.restapi.service;

import mg.restaurant.restapi.dto.MenuDTO;
import mg.restaurant.restapi.model.Category;
import mg.restaurant.restapi.model.Menu;
import mg.restaurant.restapi.model.Restaurant;
import mg.restaurant.restapi.repository.CategoryRepository;
import mg.restaurant.restapi.repository.MenuRepository;
import mg.restaurant.restapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Menu> findAll() {
        return repository.findAll();
    }

    public Menu findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Menu> findByRestaurant(Long restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    public List<Menu> findByPrice(double min, double max) {
        return repository.findByPriceBetween(min, max);
    }

    public Menu save(MenuDTO dto) {
        Menu menu = new Menu();
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElseThrow();
        menu.setRestaurant(restaurant);

        if (dto.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            menu.setCategories(categories);
        }

        return repository.save(menu);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
