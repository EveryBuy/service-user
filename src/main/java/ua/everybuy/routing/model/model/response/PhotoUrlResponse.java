package ua.everybuy.routing.model.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoUrlResponse implements ResponseMarker{
    private String userPhotoUrl;
}
