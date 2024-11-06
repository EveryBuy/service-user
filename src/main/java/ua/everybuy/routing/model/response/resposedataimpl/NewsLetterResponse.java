package ua.everybuy.routing.model.response.resposedataimpl;

import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.model.response.ResponseMarker;

@Getter
@Setter
public class NewsLetterResponse implements ResponseMarker {
    private final String message = "All letters have been sent";
}
