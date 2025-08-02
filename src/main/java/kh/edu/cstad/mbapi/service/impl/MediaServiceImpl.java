package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Media;
import kh.edu.cstad.mbapi.dto.MediaResponse;
import kh.edu.cstad.mbapi.repository.MediaRepository;
import kh.edu.cstad.mbapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Resource downloadMedia(String filename) {
        Path filePath = Paths.get(serverPath).resolve(filename).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException ignored) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
        if (!resource.exists() || !resource.isReadable()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
        return resource;
    }

    @Override
    public void deleteMedia(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filename");
        }

        String name = filename.substring(0, lastDotIndex);
        String extension = filename.substring(lastDotIndex + 1);

        Media media = mediaRepository.findByNameAndExtension(name, extension);
        if (media == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found");
        }

        media.setIsDeleted(true);
        mediaRepository.save(media);

        Path path = Paths.get(serverPath, filename);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete media file");
            }
        }
    }


    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files) {
        return files.stream().map(this::upload).collect(Collectors.toList());
    }
}
