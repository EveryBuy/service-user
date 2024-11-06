package ua.everybuy.routing.model.response.resposedataimpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.everybuy.routing.model.response.ResponseMarker;

@Data
@AllArgsConstructor
public class PhotoUrlResponse implements ResponseMarker {
    private String userPhotoUrl;
}
