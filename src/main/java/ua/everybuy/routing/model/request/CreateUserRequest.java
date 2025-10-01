package ua.everybuy.routing.model.request;

public record CreateUserRequest(long userId, String userName, String userPhotoUrl) {
}
