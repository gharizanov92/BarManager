import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kayne on 25.5.2014 г..
 */
public class Main {
    public static void main(String[] args) throws Exception{
        long tStart = System.currentTimeMillis();

        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy.mm.dd");
        String formatted = df.format(now);
        now = df.parse(formatted);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int hour = cal.get(Calendar.HOUR);
        System.out.println(hour);
    }
}
