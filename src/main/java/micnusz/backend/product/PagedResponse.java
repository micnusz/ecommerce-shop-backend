package micnusz.backend.product;

import java.util.List;

public record PagedResponse<T>(
        List<T> products,
        int total, int skip, int limit) {

}
