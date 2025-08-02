package kh.edu.cstad.mbapi.controller;


import kh.edu.cstad.mbapi.dto.MediaResponse;
import kh.edu.cstad.mbapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;
    @Value("${media.server-path}")
    private String serverPath;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }


    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename){
        Resource resource = mediaService.downloadMedia(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{filename:.+}")
    public void deleteFile(@PathVariable String filename){
        mediaService.deleteMedia(filename);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    public List<MediaResponse> uploadMedia(@RequestPart List<MultipartFile> files) {
        return mediaService.uploadMultiple(files);
    }
}
