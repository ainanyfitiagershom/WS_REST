package mg.restaurant.restapi.controller;

import mg.restaurant.restapi.model.Restaurant;
import mg.restaurant.restapi.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @GetMapping
    public CollectionModel<EntityModel<Restaurant>> getAll(@RequestParam(required = false) String name) {
        List<Restaurant> list = (name != null) ? service.findByName(name) : service.findAll();
        List<EntityModel<Restaurant>> models = list.stream().map(this::toModel).toList();
        return CollectionModel.of(models,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class).getAll(null)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Restaurant> getById(@PathVariable Long id) {
        return toModel(service.findById(id));
    }

    @PostMapping
    public EntityModel<Restaurant> create(@RequestBody Restaurant restaurant) {
        return toModel(service.save(restaurant));
    }

    @PutMapping("/{id}")
    public EntityModel<Restaurant> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant existing = service.findById(id);
        existing.setName(restaurant.getName());
        existing.setAddress(restaurant.getAddress());
        existing.setPhone(restaurant.getPhone());
        return toModel(service.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Restaurant> toModel(Restaurant r) {
        return EntityModel.of(r,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class).getById(r.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class).getAll(null)).withRel("restaurants"));
    }
}
