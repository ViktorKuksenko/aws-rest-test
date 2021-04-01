package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

  public Properties getProperties(String pathToFile) {
    Properties properties = null;
    try (InputStream input = new FileInputStream(pathToFile)) {
      properties = new Properties();
      properties.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return properties;
  }

}
