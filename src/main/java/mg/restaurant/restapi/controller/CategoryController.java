package mg.restaurant.restapi.controller;

import mg.restaurant.restapi.model.Category;
import mg.restaurant.restapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public CollectionModel<EntityModel<Category>> getAll() {
        List<EntityModel<Category>> models = service.findAll().stream().map(this::toModel).toList();
        return CollectionModel.of(models,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Category> getById(@PathVariable Long id) {
        return toModel(service.findById(id));
    }

    @PostMapping
    public EntityModel<Category> create(@RequestBody Category category) {
        return toModel(service.save(category));
    }

    @PutMapping("/{id}")
    public EntityModel<Category> update(@PathVariable Long id, @RequestBody Category category) {
        Category existing = service.findById(id);
        existing.setName(category.getName());
        return toModel(service.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Category> toModel(Category c) {
        return EntityModel.of(c,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getById(c.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAll()).withRel("categories"));
    }
}
