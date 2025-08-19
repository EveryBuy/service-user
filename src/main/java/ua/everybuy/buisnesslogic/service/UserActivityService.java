package ua.everybuy.buisnesslogic.service;

import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.User;

import java.util.Date;

@Service
public class UserActivityService {

    public boolean getUserActivityStatus(User user){
        Date now = new Date();
        Date lastActivityDate = user.getLastActivityDate();
        long period = now .getTime() - lastActivityDate.getTime();
        return period < 600000;
    }
}
