package org.pickly.push.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.pickly.push.provider.FirebaseAppProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicPushMessageService implements PushMessageService{

    private final FirebaseAppProvider appProvider;

    @Override
    public void sendMessage() {

    }

    public void subscribeTopic() {
        FirebaseApp firebaseApp = appProvider.getFirebaseApp("pickly-push");

        // Firebase의 토픽 구독요청 메서드를 호출한다.
        FirebaseMessaging.getInstance(firebaseApp).subscribeToTopicAsync(null, null);
    }
}
