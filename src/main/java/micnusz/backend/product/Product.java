package micnusz.backend.product;

import java.util.List;

public record Product(Integer id, String title, String description, String category, Double price,
                Double discountPercentage, Double rating, Integer stock, List<String> tags, String brand, String sku,
                Double weight, Dimensions dimensions, String warrantyInformation, String shippingInformation,
                String availabilityStatus, List<Reviews> reviews, String returnPolicy, Integer minimumOrderQuantity,
                Meta meta, List<String> images, String thumbnail) {

}
