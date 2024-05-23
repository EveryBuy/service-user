package ua.everybuy.routing.model.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Full name response")
public class FullNameResponse implements ResponseMarker{
    @Schema(description = "Full name of the user")
    private String fullName;
}
