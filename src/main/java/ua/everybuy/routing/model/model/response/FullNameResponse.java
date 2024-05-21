package ua.everybuy.routing.model.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FullNameResponse implements ResponseMarker{
    private String fullName;
}
