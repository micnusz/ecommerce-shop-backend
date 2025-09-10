package micnusz.backend.product.map;

import micnusz.backend.product.Product;
import micnusz.backend.product.dto.ProductApiDto;
import micnusz.backend.product.dto.ProductResponseDto;

public class ProductMapper {

    public static Product toDomain(ProductApiDto dto) {
        return new Product(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.category(),
                dto.price(),
                dto.discountPercentage(),
                dto.rating(),
                dto.stock(),
                dto.tags(),
                dto.brand(),
                dto.sku(),
                dto.weight(),
                dto.dimensions(),
                dto.warrantyInformation(),
                dto.shippingInformation(),
                dto.availabilityStatus(),
                dto.reviews(),
                dto.returnPolicy(),
                dto.minimumOrderQuantity(),
                dto.meta(),
                dto.images(),
                dto.thumbnail());
    }

    public static ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                product.id(),
                product.title(),
                product.description(),
                product.category(),
                product.price(),
                product.discountPercentage(),
                product.rating(),
                product.stock(),
                product.tags(),
                product.brand(),
                product.sku(),
                product.weight(),
                product.dimensions(),
                product.warrantyInformation(),
                product.shippingInformation(),
                product.availabilityStatus(),
                product.reviews(),
                product.returnPolicy(),
                product.minimumOrderQuantity(),
                product.meta(),
                product.images(),
                product.thumbnail());
    }
}
