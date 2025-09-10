package micnusz.backend.product.service;

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

    public Mono<PagedResponse<Product>> getProducts(
            String title,
            Double price,
            Double priceMin,
            Double priceMax,
            Integer skip,
            Integer limit) {
        return productClient.getAllProducts()
                .map(paged -> {
                    // Filtrujemy produkty lokalnie
                    var filtered = paged.products().stream()
                            .filter(p -> title == null || p.title().toLowerCase().contains(title.toLowerCase()))
                            .filter(p -> price == null || p.price().equals(price))
                            .filter(p -> priceMin == null || p.price() >= priceMin)
                            .filter(p -> priceMax == null || p.price() <= priceMax)
                            .toList();

                    // Paginacja lokalna
                    int fromIndex = Math.min(skip, filtered.size());
                    int toIndex = Math.min(skip + limit, filtered.size());
                    var page = filtered.subList(fromIndex, toIndex);

                    return new PagedResponse<>(
                            page,
                            filtered.size(), // total po filtrze
                            skip,
                            limit);
                });
    }

    public Mono<Product> getProduct(Integer id) {
        return productClient.getProductById(id);
    }
}
