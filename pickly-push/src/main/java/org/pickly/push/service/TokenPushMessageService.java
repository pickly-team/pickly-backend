package org.pickly.push.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.pickly.push.provider.FirebaseAppProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenPushMessageService implements PushMessageService {

    private final FirebaseAppProvider appProvider;

    @Override
    public void sendMessage() {
        // 푸시 메시지를 보낼 때 대상 프로젝트를 가져온다.  ( 현재 프로젝트 이름은 가제로 설정되어있다. )
        FirebaseApp firebaseApp = appProvider.getFirebaseApp("pickly-push");

        // FCM 푸시 메시지 발송을 할때 위 Firebase App의 인스턴스를 가져와, 생성한 메시지 객체를 해당 인스턴스에 전달한다.
        // 현재 예제를 위해 null로 처리한 상태
        FirebaseMessaging.getInstance(firebaseApp).sendAsync(null);
    }
}
