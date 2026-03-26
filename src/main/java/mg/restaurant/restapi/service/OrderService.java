package mg.restaurant.restapi.service;

import mg.restaurant.restapi.dto.OrderDTO;
import mg.restaurant.restapi.model.Menu;
import mg.restaurant.restapi.model.Order;
import mg.restaurant.restapi.model.Restaurant;
import mg.restaurant.restapi.model.User;
import mg.restaurant.restapi.repository.MenuRepository;
import mg.restaurant.restapi.repository.OrderRepository;
import mg.restaurant.restapi.repository.RestaurantRepository;
import mg.restaurant.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Order> findByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Order> findByRestaurant(Long restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    public Order save(OrderDTO dto) {
        Order order = new Order();

        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElseThrow();
        List<Menu> items = menuRepository.findAllById(dto.getMenuIds());

        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setItems(items);
        order.setTotal(items.stream().mapToDouble(Menu::getPrice).sum());

        return repository.save(order);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
