package kz.baltabay.assel.courseplatform.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselFileDto;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselFileAttachment;
import kz.baltabay.assel.courseplatform.exception.BaltabayAsselBadRequestException;
import kz.baltabay.assel.courseplatform.exception.BaltabayAsselNotFoundException;
import kz.baltabay.assel.courseplatform.mapper.BaltabayAsselFileMapper;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselFileAttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaltabayAsselFileStorageService {
    private final BaltabayAsselFileAttachmentRepository fileRepository;
    private final BaltabayAsselFileMapper fileMapper;

    @Value("${app.files.upload-dir}")
    private String uploadDir;

    public BaltabayAsselFileDto upload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new BaltabayAsselBadRequestException("File is empty");
        }
        try {
            Path root = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(root);
            String original = multipartFile.getOriginalFilename() == null ? "file" : multipartFile.getOriginalFilename();
            String stored = UUID.randomUUID() + "-" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
            Path target = root.resolve(stored).normalize();
            multipartFile.transferTo(target);

            BaltabayAsselFileAttachment file = new BaltabayAsselFileAttachment();
            file.setOriginalFileName(original);
            file.setStoredFileName(stored);
            file.setContentType(multipartFile.getContentType() == null ? "application/octet-stream" : multipartFile.getContentType());
            file.setSize(multipartFile.getSize());
            file.setPath(target.toString());
            BaltabayAsselFileAttachment saved = fileRepository.save(file);
            log.info("Uploaded file {} as {}", original, stored);
            return fileMapper.toDto(saved);
        } catch (IOException ex) {
            throw new BaltabayAsselBadRequestException("Could not store file");
        }
    }

    public BaltabayAsselFileAttachment findMetadata(Long id) {
        return fileRepository.findById(id).orElseThrow(() -> new BaltabayAsselNotFoundException("File not found"));
    }

    public Resource download(Long id) {
        BaltabayAsselFileAttachment metadata = findMetadata(id);
        try {
            Resource resource = new UrlResource(Path.of(metadata.getPath()).toUri());
            if (!resource.exists()) {
                throw new BaltabayAsselNotFoundException("File content not found");
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new BaltabayAsselBadRequestException("Invalid file path");
        }
    }
}
