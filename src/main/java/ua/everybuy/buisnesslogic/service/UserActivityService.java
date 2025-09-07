package ua.everybuy.buisnesslogic.service;

import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
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
        Date date = user.getLastActivityDate();
        if(date == null){
            date = new Date(2025, Calendar.FEBRUARY, 1);
        }
        LocalDateTime lastActivityLdt = LocalDateTime
                .ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate now = LocalDate.now();
        LocalDate lastActivity = lastActivityLdt.toLocalDate();
        String infoAboutLastActivity = now.equals(lastActivity) ? "today "
                : now.equals(lastActivity.minusDays(1)) ? "yesterday "
                : "";
        return infoAboutLastActivity + lastActivityLdt;
    }
}
