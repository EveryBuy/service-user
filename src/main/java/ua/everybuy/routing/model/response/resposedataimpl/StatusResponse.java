package ua.everybuy.routing.model.response.resposedataimpl;

import lombok.*;
import ua.everybuy.routing.model.response.ResponseMarker;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusResponse{
    private int status;
    private ResponseMarker data;
}
