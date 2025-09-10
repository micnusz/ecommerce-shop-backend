package micnusz.backend.product;

import java.time.Instant;

public record Reviews(Double rating, String comment, Instant date, String reviewerName, String reviewerEmail) {

}
