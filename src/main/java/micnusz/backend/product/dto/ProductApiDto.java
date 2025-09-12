package micnusz.backend.product.dto;

import java.util.List;

import micnusz.backend.product.Dimensions;
import micnusz.backend.product.Meta;
import micnusz.backend.product.Reviews;

public record ProductApiDto(Integer id, String title, String description, String category, Double price,
        Double discountPercentage, Double rating, Integer stock, List<String> tags, String brand, String sku,
        Double weight, Dimensions dimensions, String warrantyInformation, String shippingInformation,
        String availabilityStatus, List<Reviews> reviews, String returnPolicy, Integer minimumOrderQuantity,
        Meta meta, List<String> images, String thumbnail, String slug) {
}