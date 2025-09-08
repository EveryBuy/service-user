package ua.everybuy.routing.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.model.response.ResponseMarker;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ShortUserInfoDto implements ResponseMarker {
    private long userId;
    private String fullName;
    private String photoUrl;
    private boolean isOnline;
    private Date lastActivity;
}
