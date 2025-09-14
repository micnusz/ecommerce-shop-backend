package micnusz.backend.product.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import micnusz.backend.SortField;
import micnusz.backend.SortOrder;
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
            SortField sortBy,
            SortOrder order,
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

                    var sorted = sortProducts(filtered, sortBy, order);

                    int fromIndex = Math.min(skip, sorted.size());
                    int toIndex = Math.min(skip + limit, sorted.size());
                    var page = sorted.subList(fromIndex, toIndex);

                    return new PagedResponse<>(page, sorted.size(), skip, limit);
                });
    }

    private List<Product> sortProducts(List<Product> products, SortField sortBy, SortOrder order) {
        if (sortBy == null) {
            return products;
        }

        Comparator<Product> comparator = getComparator(sortBy);

        if (order == SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        return products.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private Comparator<Product> getComparator(SortField sortBy) {
        return switch (sortBy) {
            case PRICE -> Comparator.comparing(
                    Product::price,
                    Comparator.nullsLast(Comparator.naturalOrder()));
            case RATING -> Comparator.comparing(
                    Product::rating,
                    Comparator.nullsLast(Comparator.naturalOrder()));
            case TITLE -> Comparator.comparing(
                    Product::title,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
        };
    }

    public Mono<Product> getProduct(Integer id) {
        return productClient.getProductById(id);
    }
}
