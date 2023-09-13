package org.pickly.service.common.utils.encrypt;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
    byte[] keyBytes = key.getBytes();
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
      throw new EncryptException.FailToEncryptException();
    }
  }

  public Long decrypt(String encryptedValue) {
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, generateKey());
      byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
      String decryptedString = new String(decryptedBytes);
      return Long.parseLong(decryptedString);
    } catch (Exception e) {
      e.printStackTrace();
      throw new EncryptException.FailToDecryptException();
    }
  }

}
