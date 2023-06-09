package timer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
/**
 * this class returns the time right now
 * by getting local time and converting it to wanted format.
 */
public class TimerHandler {
	@SuppressWarnings("unused")
	public static String GetCurrentTimestamp() {
	    LocalDateTime localDateTime = LocalDateTime.now();
	    ZoneId zoneId = ZoneId.systemDefault();
	    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
	    long currentTimestamp = zonedDateTime.toInstant().toEpochMilli();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDateTime = localDateTime.format(formatter);
	    return formattedDateTime;
	}
}
