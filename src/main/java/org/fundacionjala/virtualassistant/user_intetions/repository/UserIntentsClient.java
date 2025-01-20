package org.fundacionjala.virtualassistant.user_intetions.repository;

import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentResponse;
import org.springframework.http.ResponseEntity;

public interface UserIntentsClient {
    ResponseEntity<IntentResponse> processUserIntentsByMicroService(String input);
}
