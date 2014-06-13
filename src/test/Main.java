import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

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
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int month = cal.get(Calendar.MONTH);
        System.out.println(month);
        SimpleDateFormat df = new SimpleDateFormat("yyyy:mm:dd:hh:mm:ss");
        Thread.sleep(1000);
        Date after = new Date();
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        System.out.println(elapsedSeconds);
    }
}
