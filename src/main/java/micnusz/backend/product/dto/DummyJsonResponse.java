package micnusz.backend.product.dto;

import java.util.List;

public record DummyJsonResponse(
                List<ProductApiDto> products,
                int total,
                int skip,
                int limit) {
}
