package ua.everybuy.routing.model.response.resposedataimpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.everybuy.routing.model.response.ResponseMarker;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubscriberResponse implements ResponseMarker {
    private String email;
}
