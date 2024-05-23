package ua.everybuy.routing.model.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FullNameResponse implements ResponseMarker{
    private String fullName;
}
