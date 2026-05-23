package kz.baltabay.assel.courseplatform.mapper;

import java.util.stream.Collectors;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselUserDto;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselRole;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselUser;
import org.springframework.stereotype.Component;

@Component
public class BaltabayAsselUserMapper {
    public BaltabayAsselUserDto toDto(BaltabayAsselUser user) {
        return new BaltabayAsselUserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRoles().stream().map(BaltabayAsselRole::name).collect(Collectors.toSet()),
                user.getCreatedAt());
    }
}
