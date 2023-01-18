package org.pickly.push.provider;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.pickly.common.error.exception.BusinessException;
import org.pickly.common.error.exception.ErrorCode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FirebaseAppProvider {

    private static final String JSON_TYPE_SUFFIX = ".json";

    private final Map<String, FirebaseApp> firebaseApps = new HashMap<>();


    public FirebaseApp getFirebaseApp(String projectName) {
        try {
            if (firebaseApps.containsKey(projectName)) return firebaseApps.get(projectName);

            ClassPathResource resource = new ClassPathResource(projectName + JSON_TYPE_SUFFIX);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options, projectName);

            firebaseApps.put(projectName, firebaseApp);

            return firebaseApps.get(projectName);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
