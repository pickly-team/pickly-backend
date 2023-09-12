package org.pickly.service.common.utils.encrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EncryptService {

  private final ExtensionKey extensionKey;

  private static final String JSON_TYPE_SUFFIX = ".json";

  @Autowired
  public EncryptService(ApplicationContext context) {
    this.extensionKey = context.getBean(ExtensionKey.class);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ClassPathResource resource = new ClassPathResource("extension" + JSON_TYPE_SUFFIX);
      ExtensionKey key = objectMapper.readValue(resource.getInputStream(), ExtensionKey.class);
      extensionKey.setKey(key.getKey());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ExtensionKey getKey() {
    return extensionKey;
  }

}
