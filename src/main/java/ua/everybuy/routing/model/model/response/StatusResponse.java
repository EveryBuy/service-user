package ua.everybuy.routing.model.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ua.everybuy.routing.model.model.dto.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusResponse<T>{
    private int status;
    private T data;
}
