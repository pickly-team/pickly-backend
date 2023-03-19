package org.pickly.service.common.utils.base;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class AuthToken {
  private final FirebaseAuth firebaseAuth;
  public FirebaseToken getDecodedToken(String token){

    FirebaseToken decodedToken;

    try {
      decodedToken= firebaseAuth.verifyIdToken(token);
      return decodedToken;
    } catch (IllegalArgumentException | FirebaseAuthException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
    }
  }
}
