package com.khrd.file_upload_btb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponce {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
}
