package mg.restaurant.restapi.controller;

import mg.restaurant.restapi.dto.MenuDTO;
import mg.restaurant.restapi.model.Menu;
import mg.restaurant.restapi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping
    public CollectionModel<EntityModel<Menu>> getAll(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<Menu> list;
        if (minPrice != null && maxPrice != null) {
            list = service.findByPrice(minPrice, maxPrice);
        } else {
            list = service.findAll();
        }
        List<EntityModel<Menu>> models = list.stream().map(this::toModel).toList();
        return CollectionModel.of(models,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MenuController.class).getAll(null, null)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Menu> getById(@PathVariable Long id) {
        return toModel(service.findById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public CollectionModel<EntityModel<Menu>> getByRestaurant(@PathVariable Long restaurantId) {
        List<EntityModel<Menu>> models = service.findByRestaurant(restaurantId).stream().map(this::toModel).toList();
        return CollectionModel.of(models);
    }

    @PostMapping
    public EntityModel<Menu> create(@RequestBody MenuDTO dto) {
        return toModel(service.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Menu> toModel(Menu m) {
        return EntityModel.of(m,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MenuController.class).getById(m.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MenuController.class).getAll(null, null)).withRel("menus"));
    }
}
