package org.fundacionjala.virtualassistant.asrOpenAiIntegration.service;

import lombok.AllArgsConstructor;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.mongo.controller.request.RecordingRequest;
import org.fundacionjala.virtualassistant.parser.exception.ParserException;
import org.fundacionjala.virtualassistant.taskhandler.TaskHandler;
import org.fundacionjala.virtualassistant.taskhandler.exception.IntentException;
import org.fundacionjala.virtualassistant.textrequest.controller.request.TextRequest;
import org.fundacionjala.virtualassistant.textrequest.controller.response.TextRequestResponse;
import org.fundacionjala.virtualassistant.textrequest.exception.TextRequestException;
import org.fundacionjala.virtualassistant.textrequest.service.TextRequestService;
import org.fundacionjala.virtualassistant.whisper.client.WhisperClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class AsrOpenAiImplementation {
    private WhisperClient whisperClient;
    private TaskHandler taskHandler;
    private TextRequestService textRequestService;

    public String asrOpenAIResponse(MultipartFile multipartFile) throws IOException, IntentException {
        String request = cleanText(whisperClient.convertToText(multipartFile));
        return taskHandler.handleIntent(request);
    }

    public TextRequestResponse asrOpenAIResponse(RecordingRequest recordingRequest, String idAudioMongo)
            throws IOException, IntentException, ParserException, TextRequestException {
        String request = cleanText(whisperClient.convertToText(recordingRequest.getAudioFile()));

        TextRequest textRequest = createTextRequest(recordingRequest, idAudioMongo, request);

        String taskResponse = taskHandler.handleIntent(request);
        return textRequestService.save(textRequest, taskResponse);
    }

    private static TextRequest createTextRequest(RecordingRequest recordingRequest,
                                                 String idAudioMongo, String request) {
        return TextRequest.builder()
                .idAudioMongo(idAudioMongo)
                .date(ZonedDateTime.now())
                .idUser(recordingRequest.getIdUser())
                .context(ContextResponse.builder()
                        .idContext(recordingRequest.getIdChat())
                        .build())
                .text(request)
                .build();
    }

    private String cleanText(String text) {
        return text.replaceAll("\"", "");
    }
}
