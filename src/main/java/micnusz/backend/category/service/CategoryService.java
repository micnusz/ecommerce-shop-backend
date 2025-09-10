package micnusz.backend.category.service;

import org.springframework.stereotype.Service;

import micnusz.backend.category.Category;
import micnusz.backend.category.CategoryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final CategoryClient categoryClient;

    public CategoryService(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }

    public Flux<Category> getCategories() {
        return categoryClient.getCategories();
    }

    public Mono<Category> getCategoryById(Integer id) {
        return categoryClient.getCategoryById(id);
    }

    public Mono<Category> getCategoryBySlug(String slug) {
        return categoryClient.getCategoryBySlug(slug);
    }
}
