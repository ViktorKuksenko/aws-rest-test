package helpers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Sha256Utils {

  public static byte[] generateSHA256Hash(String messageToConvert, String algorithm) {
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      noSuchAlgorithmException.printStackTrace();
    }
    return digest.digest(messageToConvert.getBytes(StandardCharsets.UTF_8));
  }

  public static String getHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  public static byte[] getHmacSHA256(String data, byte[] key) throws NoSuchAlgorithmException
      , InvalidKeyException, UnsupportedEncodingException {
    String algorithm = "HmacSHA256";
    Mac mac = Mac.getInstance(algorithm);
    mac.init(new SecretKeySpec(key, algorithm));
    return mac.doFinal(data.getBytes("UTF-8"));
  }

  public static byte[] getSigInKey(String key, String dateStamp, String regionName
      , String serviceName) throws NoSuchAlgorithmException, InvalidKeyException
      , UnsupportedEncodingException {
    byte[] kSecret = ("AWS4" + key).getBytes("UTF-8");
    byte[] kDate = getHmacSHA256(dateStamp, kSecret);
    byte[] kRegion = getHmacSHA256(regionName, kDate);
    byte[] kService = getHmacSHA256(serviceName, kRegion);
    byte[] kSigning = getHmacSHA256("aws4_request", kService);
    return kSigning;
  }

}
