package kh.edu.cstad.mbapi.controller;


import kh.edu.cstad.mbapi.dto.MediaResponse;
import kh.edu.cstad.mbapi.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }
}
