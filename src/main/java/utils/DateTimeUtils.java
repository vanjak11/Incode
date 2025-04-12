package utils;


public class DateTimeUtils extends LoggerUtils {

  public static void wait(int seconds) {
    try {
      Thread.sleep(1000 * seconds);
    }
    catch (InterruptedException e) {
      log.warn("InterruptedException in Thread.sleep(" + seconds + "). Message: " + e.getMessage());
    }
  }
}
