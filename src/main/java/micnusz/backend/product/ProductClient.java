package micnusz.backend.product;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import micnusz.backend.product.dto.DummyJsonResponse;
import micnusz.backend.product.dto.ProductApiDto;
import micnusz.backend.product.map.ProductMapper;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://dummyjson.com").build();
    }

    public Mono<PagedResponse<Product>> getAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToMono(DummyJsonResponse.class)
                .map(resp -> new PagedResponse<>(
                        resp.products().stream()
                                .map(ProductMapper::toDomain)
                                .toList(),
                        resp.total(),
                        0,
                        resp.products().size()));
    }

    public Mono<Product> getProductById(Integer id) {
        return webClient.get().uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(ProductApiDto.class)
                .map(ProductMapper::toDomain);
    }

    public Mono<List<String>> getAllBrands() {
        return getAllProducts()
                .map(paged -> paged.products().stream()
                        .map(Product::brand)
                        .filter(Objects::nonNull)
                        .distinct()
                        .toList());
    }

    public Mono<List<String>> getAllCategories() {
        return getAllProducts()
                .map(paged -> paged.products().stream()
                        .map(Product::category)
                        .distinct()
                        .toList());
    }

}
