package ua.everybuy.routing.model.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ua.everybuy.routing.model.model.dto.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response status")
public class StatusResponse<T>{
    @Schema(description = "HTTP status code")
    private int status;

    @Schema(description = "Data response", oneOf = {FullNameResponse.class, UserDto.class, PhotoUrlResponse.class})
    private T data;
}
