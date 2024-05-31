package ua.everybuy.routing.model.response;

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
