package org.fundacionjala.virtualassistant.taskhandler.intents;

import lombok.Builder;
import lombok.Getter;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;

@Getter
@Builder
public class EntityArgs {
    private IntentEntity primaryArg;
    private IntentEntity secondaryArg;
    private String text;
}
