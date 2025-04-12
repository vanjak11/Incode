package utils;

import java.io.InputStream;
import java.util.Properties;

import org.testng.Assert;

//this class implements logic for reading properties from common.properties file
public class PropertiesUtils {

  private static final String sPropertiesPath = "common.properties";
  private static final Properties properties = loadPropertiesFile();

  public static Properties loadPropertiesFile(String sFilePath) {
    Properties properties = new Properties();
    InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
    try {
      properties.load(inputStream);
    }
    catch (Exception e) {
      Assert.fail("Cannot load " + sFilePath + " file. Message: " + e.getMessage());
    }
    return properties;
  }

  private static Properties loadPropertiesFile() {
    return loadPropertiesFile(sPropertiesPath);
  }

  private static String getProperty(String sProperty) {
    String sResult = properties.getProperty(sProperty);
    Assert.assertNotNull(sResult, "Cannot find property " + sProperty + " in Properties file");
    return sResult;
  }

  public static String getDriverPath() {
    return getProperty("driverPath");
  }

  public static String getBrowser() {
    return getProperty("browser");
  }

  public static String getUrl() {
    return getProperty("baseUrl");
  }

  public static String getUserName() {
    return getProperty("userName");
  }

  public static String getPassword() {
    return getProperty("password");
  }
}
