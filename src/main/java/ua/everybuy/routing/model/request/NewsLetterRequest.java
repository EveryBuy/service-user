package ua.everybuy.routing.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsLetterRequest {
    private String subject;
    private String text;
    private String internalPassword;
}
