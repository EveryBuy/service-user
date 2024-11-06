package ua.everybuy.routing.model.response.resposedataimpl;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private MessageResponse error;
}
