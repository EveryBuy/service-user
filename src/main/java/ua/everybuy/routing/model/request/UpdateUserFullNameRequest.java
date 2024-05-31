package ua.everybuy.routing.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserFullNameRequest(@NotNull(message = "User full name should be not null")
                                        @Size(min = 2, max = 40,
                                                message = "User full name should be between 2 and 40 characters")
                                        @Pattern(regexp = "[A-Za-zА-Яа-я ІіЇїЄє'Ґґ]*",
                                                message = "Name should contains latin or cyrillic alphabet letters only")
                                        String fullName) {
}
