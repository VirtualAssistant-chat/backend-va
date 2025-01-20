package org.fundacionjala.virtualassistant.taskhandler.actions;

import org.fundacionjala.virtualassistant.player.spotify.service.MusicService;
import org.fundacionjala.virtualassistant.taskhandler.TaskAction;
import org.fundacionjala.virtualassistant.taskhandler.intents.EntityArgs;
import org.springframework.stereotype.Component;

@Component
public class GetPlayerAction implements TaskAction {
    private MusicService musicService;

    public GetPlayerAction(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public String handleAction(EntityArgs intentEntities, String text) {
        return musicService.getUserPlayerInformation().getBody();
    }
}
