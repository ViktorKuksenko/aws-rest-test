package helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtils {

  public static <T> T getDeserializedXml(String xml, Class<T> clazz) {
    XmlMapper mapper = new XmlMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    T deserializedXml = null;
    try {
      deserializedXml = mapper.readValue(xml, clazz);
    } catch (JsonProcessingException exception) {
      exception.getCause();
      exception.printStackTrace();
    }
    return deserializedXml;
  }

}
