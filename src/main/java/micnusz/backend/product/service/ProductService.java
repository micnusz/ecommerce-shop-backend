package micnusz.backend.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import micnusz.backend.product.PagedResponse;
import micnusz.backend.product.Product;
import micnusz.backend.product.ProductClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public Mono<List<String>> getBrands() {
        return productClient.getAllBrands();
    }

    public Mono<List<String>> getCategories() {
        return productClient.getAllCategories();
    }

    public Mono<PagedResponse<Product>> getProducts(
            String title,
            Double price,
            Double minPrice,
            Double maxPrice,
            List<String> categories,
            List<String> brands,
            Integer rating,
            Double minRating,
            Double maxRating,
            Integer skip,
            Integer limit) {
        return productClient.getAllProducts()
                .map(paged -> {
                    var filtered = paged.products().stream()
                            .filter(p -> title == null || p.title().toLowerCase().contains(title.toLowerCase()))
                            .filter(p -> price == null || p.price().equals(price))
                            .filter(p -> minPrice == null || p.price() >= minPrice)
                            .filter(p -> maxPrice == null || p.price() <= maxPrice)
                            .filter(p -> categories == null || categories.isEmpty()
                                    || categories.contains(p.category()))
                            .filter(p -> brands == null || brands.isEmpty() ||
                                    (p.brand() != null && brands.stream()
                                            .anyMatch(b -> b.equalsIgnoreCase(p.brand()))))

                            .filter(p -> rating == null || p.rating().equals(rating))
                            .filter(p -> minRating == null || p.rating() >= minRating)
                            .filter(p -> maxRating == null || p.rating() <= maxRating)
                            .toList();

                    int fromIndex = Math.min(skip, filtered.size());
                    int toIndex = Math.min(skip + limit, filtered.size());
                    var page = filtered.subList(fromIndex, toIndex);

                    return new PagedResponse<>(page, filtered.size(), skip, limit);
                });
    }

    public Mono<Product> getProduct(Integer id) {
        return productClient.getProductById(id);
    }
}
