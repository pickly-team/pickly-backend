package org.pickly.service.common.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

  private static final String JSON_TYPE_SUFFIX = ".json";

  @Bean
  public FirebaseAuth firebaseAuth() throws IOException {

    ClassPathResource resource = new ClassPathResource("firebase" + JSON_TYPE_SUFFIX);

    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
        .build();

    FirebaseApp.initializeApp(options);
    return FirebaseAuth.getInstance(FirebaseApp.getInstance());
  }
}
