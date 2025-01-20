package org.fundacionjala.virtualassistant.taskhandler;

import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.taskhandler.factory.IntentFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionFactory;
import org.fundacionjala.virtualassistant.taskhandler.factory.TaskActionManagerFactory;
import org.fundacionjala.virtualassistant.taskhandler.intents.EntityArgs;
import org.fundacionjala.virtualassistant.taskhandler.intents.EntityConverter;
import org.fundacionjala.virtualassistant.taskhandler.intents.Intent;
import org.fundacionjala.virtualassistant.taskhandler.intents.IntentManager;
import org.fundacionjala.virtualassistant.user_intetions.client.RasaClient;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class Proxy {
    private RasaClient rasaClient;
    private IntentFactory intentFactory;
    private TaskActionManagerFactory taskActionManagerFactory;

    @Autowired
    public Proxy (RasaClient rasaClient, IntentFactory intentFactory, TaskActionManagerFactory taskActionManagerFactory) {
        this.rasaClient = rasaClient;
        this.intentFactory = intentFactory;
        this.taskActionManagerFactory = taskActionManagerFactory;
    }

    public String handleIntent(String text) throws IntentException {
        IntentResponse intentResponse = processIntent(text);
        String userIntent = intentResponse.getIntent().getName();
        List<IntentEntity> intentEntities = intentResponse.getIntentEntities();
        EntityArgs entityArgs = EntityConverter.convert(intentEntities);

        return handleAction(userIntent, entityArgs, text);
    }

    public String handleAction(String userIntent, EntityArgs intentEntities, String text) throws IntentException {
        taskActionManagerFactory.setIntentType(userIntent);
        TaskActionFactory taskActionFactory = taskActionManagerFactory.getTaskActionFactory(userIntent);

        Intent intent = taskActionManagerFactory.getIntent(userIntent);
        IntentManager intentManager = intentFactory.getSpecific(intent);

        return taskActionFactory
                .createTaskAction(intentManager.processIntent(userIntent))
                .handleAction(intentEntities, text);
    }

    private IntentResponse processIntent(String text) throws IntentException {
        IntentResponse response = rasaClient.processUserIntentsByMicroService(text).getBody();
        if (Optional.ofNullable(response).isEmpty()) {
            throw new IntentException(IntentException.INTENT_NOT_FOUND);
        }
        return response;
    }
}