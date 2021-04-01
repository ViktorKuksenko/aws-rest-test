package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {

  public static String getFileChecksum(File file) {
    MessageDigest digest = null;
    byte[] byteArray = new byte[1024];
    int bytesCount = 0;
    try (FileInputStream fis = new FileInputStream(file)) {
      digest = MessageDigest.getInstance("SHA-256");

      while ((bytesCount = fis.read(byteArray)) != -1) {
        digest.update(byteArray, 0, bytesCount);
      }
    } catch (IOException | NoSuchAlgorithmException exception) {
      exception.printStackTrace();
    }

    byte[] bytes = digest.digest();

    return Sha256Utils.getHexString(bytes);
  }

}
