package ua.everybuy.routing.model.response.resposedataimpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.model.response.ResponseMarker;

@Getter
@Setter
@AllArgsConstructor
public class FullNameResponse implements ResponseMarker {
    private String fullName;
}
