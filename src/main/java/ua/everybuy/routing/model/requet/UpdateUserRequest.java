package ua.everybuy.routing.model.requet;

import jakarta.validation.constraints.NotNull;


public record UpdateUserRequest (@NotNull(message = "Field userId should be not null") Long userId,
                                 @NotNull(message = "Field email should be not null") String email,
                                 @NotNull(message = "Field phone should be not null") String phone,
                                 @NotNull(message = "Field fullName should be not null") String fullName,
                                 @NotNull(message = "Field userPhotoUrl should be not null") String userPhotoUrl) {
}
