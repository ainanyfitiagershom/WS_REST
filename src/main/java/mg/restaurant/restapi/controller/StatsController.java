package mg.restaurant.restapi.controller;

import mg.restaurant.restapi.model.Order;
import mg.restaurant.restapi.model.Restaurant;
import mg.restaurant.restapi.model.User;
import mg.restaurant.restapi.repository.OrderRepository;
import mg.restaurant.restapi.repository.RestaurantRepository;
import mg.restaurant.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/revenue")
    public List<Map<String, Object>> revenueByRestaurant() {
        List<Map<String, Object>> stats = new ArrayList<>();
        for (Restaurant r : restaurantRepository.findAll()) {
            List<Order> orders = orderRepository.findByRestaurantId(r.getId());
            Map<String, Object> stat = new HashMap<>();
            stat.put("restaurantId", r.getId());
            stat.put("restaurantName", r.getName());
            stat.put("totalOrders", orders.size());
            stat.put("totalRevenue", orders.stream().mapToDouble(Order::getTotal).sum());
            stats.add(stat);
        }
        return stats;
    }

    @GetMapping("/users")
    public List<Map<String, Object>> statsByUser() {
        List<Map<String, Object>> stats = new ArrayList<>();
        for (User u : userRepository.findAll()) {
            List<Order> orders = orderRepository.findByUserId(u.getId());
            Map<String, Object> stat = new HashMap<>();
            stat.put("userId", u.getId());
            stat.put("email", u.getEmail());
            stat.put("totalOrders", orders.size());
            stat.put("totalSpent", orders.stream().mapToDouble(Order::getTotal).sum());
            stats.add(stat);
        }
        return stats;
    }
}
