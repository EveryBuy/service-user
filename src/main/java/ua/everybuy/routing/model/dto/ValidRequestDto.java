package ua.everybuy.routing.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidRequestDto {
    private int status;
    private AuthUserInfoDto data;
}
