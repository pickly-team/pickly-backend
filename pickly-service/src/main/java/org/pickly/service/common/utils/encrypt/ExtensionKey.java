package org.pickly.service.common.utils.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class ExtensionKey {

  private String key;
  private static final String ALGORITHM = "AES";

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  private SecretKey generateKey() {
    System.out.println("key = " + key);
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, ALGORITHM);
  }

  public String encrypt(Long value) {
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, generateKey());
      byte[] encrypted = cipher.doFinal(Long.toString(value).getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
