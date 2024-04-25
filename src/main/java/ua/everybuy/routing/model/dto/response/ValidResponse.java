package ua.everybuy.routing.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidResponse {
    private int status;
    private UserInfoDTO data;

//    public boolean isUserValid(){
//        return data.isValid;
//    }

    @Setter
    @Getter
    public static class UserInfoDTO{

        private Boolean isValid;
        private long userId;
        private String email;
        private String phoneNumber;
        private List<String> roles;

        @Override
        public String toString() {
            return "UserInfoDTO{" +
                    "isValid=" + isValid +
                    ", userId=" + userId +
                    ", email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", roles=" + roles +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AuthResponseDTO{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
