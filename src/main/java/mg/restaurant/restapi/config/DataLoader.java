package mg.restaurant.restapi.config;

import mg.restaurant.restapi.model.*;
import mg.restaurant.restapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired UserRepository userRepo;
    @Autowired RestaurantRepository restaurantRepo;
    @Autowired CategoryRepository categoryRepo;
    @Autowired MenuRepository menuRepo;
    @Autowired OrderRepository orderRepo;

    @Override
    public void run(String... args) {
        // Users
        User admin = new User("admin@test.com", "1234", "ADMIN");
        User user = new User("user@test.com", "1234", "USER");
        userRepo.saveAll(List.of(admin, user));

        // Categories
        Category pizza = new Category("Pizza");
        Category pasta = new Category("Pates");
        Category dessert = new Category("Dessert");
        Category boisson = new Category("Boisson");
        categoryRepo.saveAll(List.of(pizza, pasta, dessert, boisson));

        // Restaurants
        Restaurant r1 = new Restaurant("Chez Luigi", "10 rue de Nice", "0493001122");
        Restaurant r2 = new Restaurant("La Dolce Vita", "25 av de la Liberte", "0493334455");
        restaurantRepo.saveAll(List.of(r1, r2));

        // Menus
        Menu m1 = new Menu("Margherita", "Tomate, mozzarella, basilic", 9.0, r1);
        m1.setCategories(List.of(pizza));
        Menu m2 = new Menu("Carbonara", "Creme, lardons, parmesan", 12.0, r1);
        m2.setCategories(List.of(pasta));
        Menu m3 = new Menu("Tiramisu", "Mascarpone, cafe, cacao", 7.0, r1);
        m3.setCategories(List.of(dessert));
        Menu m4 = new Menu("Quattro Formaggi", "4 fromages italiens", 13.0, r2);
        m4.setCategories(List.of(pizza));
        Menu m5 = new Menu("Limonade maison", "Citron presse, menthe", 4.0, r2);
        m5.setCategories(List.of(boisson));
        menuRepo.saveAll(List.of(m1, m2, m3, m4, m5));

        // Orders
        Order o1 = new Order();
        o1.setUser(user);
        o1.setRestaurant(r1);
        o1.setItems(List.of(m1, m2));
        o1.setTotal(21.0);

        Order o2 = new Order();
        o2.setUser(user);
        o2.setRestaurant(r2);
        o2.setItems(List.of(m4));
        o2.setTotal(13.0);

        Order o3 = new Order();
        o3.setUser(admin);
        o3.setRestaurant(r1);
        o3.setItems(List.of(m1, m3));
        o3.setTotal(16.0);

        orderRepo.saveAll(List.of(o1, o2, o3));
    }
}
