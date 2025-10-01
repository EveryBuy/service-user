package ua.everybuy.routing.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UserRatingRequest(long userId, @Min(1) @Max(5) short userRating) {
}
