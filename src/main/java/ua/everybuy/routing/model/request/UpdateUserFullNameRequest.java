package ua.everybuy.routing.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserFullNameRequest(@NotNull(message = "User full name should be not null")
                                        @Size(min = 2, max = 20, message = "User full name should be between 2 and 20 characters")
                                        String fullName) {
}
