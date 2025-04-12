package utils;

import java.lang.invoke.MethodHandles;
import org.slf4j.LoggerFactory;


public class LoggerUtils {
  public static final org.slf4j.Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

}
