package helpers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

  public static String getTimeStamp(OffsetDateTime dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    String formatDateTime = dateTime.format(formatter);
    return formatDateTime;
  }

  public static String getDate(OffsetDateTime dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formatDateTime = dateTime.format(formatter);
    return formatDateTime;
  }

  public static String getCurrentLocalTimestamp() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    LocalDateTime now = LocalDateTime.now();
    return now.format(formatter);
  }
}
