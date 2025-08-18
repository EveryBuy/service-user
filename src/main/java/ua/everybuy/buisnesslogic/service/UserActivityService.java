package ua.everybuy.buisnesslogic.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.User;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserActivityService {
    private final UserService userService;
    private final InternalValidateService validateService;

    public Date changeLastUsersActivityTime(long userId, HttpServletRequest request){
        validateService.validatePassword(request);
        User user = userService.getUserById(userId);
        user.setLastActivityDate(new Date());
        userService.updateUser(user);
        return user.getLastActivityDate();
    }
}
