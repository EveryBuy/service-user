package ua.everybuy.routing.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FullNameResponse implements ResponseMarker{
    private String fullName;
}
