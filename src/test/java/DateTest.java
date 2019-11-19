import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTest {


    public static void main(String[] args) {

        System.out.println(
                ChronoUnit.valueOf("MILLIS")
        );
//        LocalDateTime start = LocalDateTime.parse("2010-01-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd " +
//                "HH:mm"));
//        System.out.println(start);
//        start = start.plusMinutes(1);
//        System.out.println(start);
//        start = start.plusMinutes(1);
//        System.out.println(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd " +
//                "HH:mm")));
//        TemporalAccessor
//        LocalTime.from()
    }
}
