package org.fundacionjala.virtualassistant.redis.controller;

import org.fundacionjala.virtualassistant.redis.entity.Audio;
import org.fundacionjala.virtualassistant.redis.exception.FileSaveException;
import org.fundacionjala.virtualassistant.redis.exception.RedisDataNotFoundException;
import org.fundacionjala.virtualassistant.redis.service.AudioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/audio/")
public class AudioController {
    AudioService audioService;

    public AudioController(AudioService audioService) {
        this.audioService = audioService;
    }

    @PostMapping("add")
    public Audio addAudio(@RequestParam("file") MultipartFile file) throws FileSaveException {
        return audioService.save(file);
    }

    @GetMapping("/{id}")
    public byte[] getAudio(@PathVariable String id) throws RedisDataNotFoundException {
        return audioService.findById(id);
    }
}
