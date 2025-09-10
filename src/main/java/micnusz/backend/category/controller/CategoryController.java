package micnusz.backend.category.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import micnusz.backend.category.dto.CategoryResponseDto;
import micnusz.backend.category.map.CategoryMapper;
import micnusz.backend.category.service.CategoryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Getting categories
    @GetMapping
    public Flux<CategoryResponseDto> getCategories() {
        return categoryService.getCategories().map(CategoryMapper::toResponse);
    }

    // Getting category by id
    @GetMapping("/{id}")
    public Mono<CategoryResponseDto> getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id).map(CategoryMapper::toResponse);
    }

    // Getting category by slug
    @GetMapping("/slug/{slug}")
    public Mono<CategoryResponseDto> getCategoryBySlug(@PathVariable String slug) {
        return categoryService.getCategoryBySlug(slug).map(CategoryMapper::toResponse);
    }

}
