package com.khrd.file_upload_btb.controller;

import com.khrd.file_upload_btb.model.ApiResponce;
import com.khrd.file_upload_btb.model.FileResponce;
import com.khrd.file_upload_btb.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    List<FileResponce> filesStore;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam List<MultipartFile> file) throws IOException {
        ApiResponce<FileResponce> response = null;
        for (MultipartFile multipartFile : file) {
            String fileName = fileService.saveFile(multipartFile);
            String fileUrl = "http://localhost:8080/" + fileName;
            FileResponce fileResponse = new FileResponce(fileName, fileUrl, multipartFile.getContentType(), multipartFile.getSize());
            response = ApiResponce.<FileResponce>builder()
                    .message("successfully uploaded file")
                    .status(HttpStatus.OK)
                    .payload(fileResponse).build();
            filesStore.add(fileResponse);
        }
        return ResponseEntity.ok(filesStore);
    }

    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam String fileName) throws IOException {
        ByteArrayResource resource = fileService.getFileByFileName(fileName);
        MediaType mediaType;
        if (fileName.endsWith(".pdf")) {
            mediaType = MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_PNG;
        } else {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(mediaType).body(resource);
    }
}

