package org.pickly.service.common.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseAuth firebaseAuth() throws IOException {
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.getApplicationDefault())
        .build();

    FirebaseApp.initializeApp(options);
    return FirebaseAuth.getInstance(FirebaseApp.getInstance());
  }
}
