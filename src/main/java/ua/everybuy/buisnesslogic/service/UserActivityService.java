package ua.everybuy.buisnesslogic.service;

import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UserActivityService {

    public boolean getUserActivityStatus(User user){
        Date now = new Date();
        Date lastActivityDate = user.getLastActivityDate();
        if(lastActivityDate ==null){
            return false;
        }
        long period = now .getTime() - lastActivityDate.getTime();
        return period < 600000;
    }

    public String getInfoAboutLastActivity(User user){
        LocalDateTime lastActivityLdt = LocalDateTime
                .ofInstant(user.getLastActivityDate().toInstant(), ZoneId.systemDefault());
        LocalDate now = LocalDate.now();
        LocalDate lastActivity = lastActivityLdt.toLocalDate();
        String infoAboutLastActivity = now.equals(lastActivity) ? "today "
                : now.equals(lastActivity.minusDays(1)) ? "yesterday "
                : "";
        return infoAboutLastActivity + lastActivityLdt;
    }
}
