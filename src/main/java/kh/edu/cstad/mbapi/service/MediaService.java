package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    /**
     * Upload single file
     * @param file from media request
     * @return Media Response
     */
    MediaResponse upload(MultipartFile file);
    Resource downloadMedia(String filename);
    void deleteMedia(String filename);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files);
}
