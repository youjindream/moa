package com.moa.planner.event.dto;

import com.moa.planner.media.MediaType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventMediaResponse {

    private Long mediaId;

    private MediaType mediaType;

    private String fileUrl;

    private String originalFilename;

    private Long fileSizeBytes;
}