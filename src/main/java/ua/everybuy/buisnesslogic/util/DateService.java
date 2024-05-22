package ua.everybuy.buisnesslogic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateService {
    public static Date setDateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        try {
            date = sdf.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
