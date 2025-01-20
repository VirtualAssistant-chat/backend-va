package org.fundacionjala.virtualassistant.asrOpenAiIntegration.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class BASE64DecodedMultipartFile implements MultipartFile {

    private byte[] audio;
    private String fileName;
    private static final String AUDIO_NAME = "audio";

    public BASE64DecodedMultipartFile (byte[] audio, String fileName) {
        this.audio = audio;
        this.fileName = fileName;
    }

    @Override
    public String getName() {
        return AUDIO_NAME;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return MediaType.MULTIPART_FORM_DATA_VALUE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return audio.length;
    }

    @Override
    public byte[] getBytes() {
        return audio;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(audio);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(audio);
    }
}