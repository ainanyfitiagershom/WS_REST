package mg.restaurant.restapi.service;

import mg.restaurant.restapi.model.Restaurant;
import mg.restaurant.restapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    public Restaurant findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Restaurant> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
