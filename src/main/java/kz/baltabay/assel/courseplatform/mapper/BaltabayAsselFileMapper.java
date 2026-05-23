package kz.baltabay.assel.courseplatform.mapper;

import kz.baltabay.assel.courseplatform.dto.BaltabayAsselFileDto;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselFileAttachment;
import org.springframework.stereotype.Component;

@Component
public class BaltabayAsselFileMapper {
    public BaltabayAsselFileDto toDto(BaltabayAsselFileAttachment file) {
        return new BaltabayAsselFileDto(file.getId(), file.getOriginalFileName(), file.getContentType(),
                file.getSize(), file.getUploadedAt());
    }
}
