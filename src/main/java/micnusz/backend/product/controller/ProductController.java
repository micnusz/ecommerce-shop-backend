package micnusz.backend.product.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import micnusz.backend.product.PagedResponse;
import micnusz.backend.product.dto.ProductResponseDto;
import micnusz.backend.product.map.ProductMapper;
import micnusz.backend.product.service.ProductService;
import reactor.core.publisher.Mono;

@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Mono<PagedResponse<ProductResponseDto>> getProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(defaultValue = "0") Integer skip,
            @RequestParam(defaultValue = "20") Integer limit) {
        return productService.getProducts(title, price, priceMin, priceMax,
                category, brand, rating, minRating, maxRating, skip, limit)
                .map(paged -> new PagedResponse<>(
                        paged.products().stream()
                                .map(ProductMapper::toResponse)
                                .toList(),
                        paged.total(),
                        paged.skip(),
                        paged.limit()));
    }

    @GetMapping("/{id}")
    public Mono<ProductResponseDto> getProduct(@PathVariable Integer id) {
        return productService.getProduct(id).map(ProductMapper::toResponse);
    }

}
