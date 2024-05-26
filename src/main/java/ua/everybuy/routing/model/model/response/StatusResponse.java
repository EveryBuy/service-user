package ua.everybuy.routing.model.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusResponse{
    private int status;
    private ResponseMarker data;
}
