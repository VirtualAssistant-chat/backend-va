package org.fundacionjala.virtualassistant.whisper.repository;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ASRClient {
    String convertToText(MultipartFile audioFile)throws IOException;

}