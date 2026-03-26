package mg.restaurant.restapi.controller;

import mg.restaurant.restapi.dto.OrderDTO;
import mg.restaurant.restapi.model.Order;
import mg.restaurant.restapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public CollectionModel<EntityModel<Order>> getAll() {
        List<EntityModel<Order>> models = service.findAll().stream().map(this::toModel).toList();
        return CollectionModel.of(models,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Order> getById(@PathVariable Long id) {
        return toModel(service.findById(id));
    }

    @GetMapping("/user/{userId}")
    public CollectionModel<EntityModel<Order>> getByUser(@PathVariable Long userId) {
        List<EntityModel<Order>> models = service.findByUser(userId).stream().map(this::toModel).toList();
        return CollectionModel.of(models);
    }

    @PostMapping
    public EntityModel<Order> create(@RequestBody OrderDTO dto) {
        return toModel(service.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Order> toModel(Order o) {
        return EntityModel.of(o,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getById(o.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAll()).withRel("orders"));
    }
}
