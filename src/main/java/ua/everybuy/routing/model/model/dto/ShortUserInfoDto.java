package ua.everybuy.routing.model.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.model.model.response.ResponseMarker;

@Getter
@Setter
@AllArgsConstructor
public class ShortUserInfoDto implements ResponseMarker {
    private long userId;
    private String fullName;
    private String photoUrl;
}
