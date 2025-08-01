package kh.edu.cstad.mbapi.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name,
        String mimeType,
        String uri,
        Long size
) {
}
