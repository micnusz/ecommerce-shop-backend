package micnusz.backend.category;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import micnusz.backend.category.dto.CategoryApiDto;
import micnusz.backend.category.map.CategoryMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CategoryClient {
    private final WebClient webClient;

    public CategoryClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.escuelajs.co/api/v1").build();
    }

    public Flux<Category> getCategories() {
        return webClient.get().uri("/categories").retrieve().bodyToFlux(CategoryApiDto.class)
                .map(CategoryMapper::toDomain);
    }

    public Mono<Category> getCategoryById(Integer id) {
        return webClient.get().uri("/categories/{id}", id).retrieve().bodyToMono(CategoryApiDto.class)
                .map(CategoryMapper::toDomain);
    }

    public Mono<Category> getCategoryBySlug(String slug) {
        return webClient.get().uri("/categories/slug/{slug}", slug).retrieve().bodyToMono(CategoryApiDto.class)
                .map(CategoryMapper::toDomain);
    }
}
