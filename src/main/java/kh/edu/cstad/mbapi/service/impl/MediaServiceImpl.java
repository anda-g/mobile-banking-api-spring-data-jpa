package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Media;
import kh.edu.cstad.mbapi.dto.MediaResponse;
import kh.edu.cstad.mbapi.repository.MediaRepository;
import kh.edu.cstad.mbapi.service.MediaService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Value("${media.server-path}")
    private String serverPath;
    @Value("${media.base-uri}")
    private String baseUri;
    private final MediaRepository mediaRepository;
    @Override
    public MediaResponse upload(MultipartFile file) {

        if(file.getOriginalFilename() == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        String name = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index);

        Path path = Paths.get(serverPath + name + extension);

        try{
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Media upload failed");
        }

        Media media = Media.builder()
                .name(name)
                .extension(extension.substring(1))
                .mimeType(file.getContentType())
                .isDeleted(false)
                .build();

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .size(file.getSize())
                .mimeType(media.getMimeType())
                .uri(baseUri + media.getName() + extension)
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files) {
        return List.of();
    }
}
