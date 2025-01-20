package org.fundacionjala.virtualassistant.taskhandler;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskHandler {
    private Proxy proxy;

    public String handleIntent(String text) throws IntentException {
        return proxy.handleIntent(text);
    }
}
