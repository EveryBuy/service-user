package ua.everybuy.routing.model.dto.response;

import lombok.*;
import ua.everybuy.routing.model.dto.AuthUserInfoDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidRequest {
    private int status;
    private AuthUserInfoDto data;
}
