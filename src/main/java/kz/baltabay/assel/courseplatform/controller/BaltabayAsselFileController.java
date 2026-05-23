package kz.baltabay.assel.courseplatform.controller;

import kz.baltabay.assel.courseplatform.dto.BaltabayAsselFileDto;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselFileAttachment;
import kz.baltabay.assel.courseplatform.service.BaltabayAsselFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class BaltabayAsselFileController {
    private final BaltabayAsselFileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaltabayAsselFileDto upload(@RequestParam("file") MultipartFile file) {
        return fileStorageService.upload(file);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        BaltabayAsselFileAttachment metadata = fileStorageService.findMetadata(id);
        Resource resource = fileStorageService.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + metadata.getOriginalFileName() + "\"")
                .body(resource);
    }
}
