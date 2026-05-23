package kz.baltabay.assel.courseplatform.service;

import kz.baltabay.assel.courseplatform.dto.BaltabayAsselCourseDtos.CourseRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselCourseDtos.CourseResponse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourseLevel;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselUser;
import kz.baltabay.assel.courseplatform.exception.BaltabayAsselNotFoundException;
import kz.baltabay.assel.courseplatform.mapper.BaltabayAsselCourseMapper;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselCourseRepository;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BaltabayAsselCourseService {
    private static final Logger log = LoggerFactory.getLogger(BaltabayAsselCourseService.class);

    private final BaltabayAsselCourseRepository courseRepository;
    private final BaltabayAsselUserRepository userRepository;
    private final BaltabayAsselCourseMapper courseMapper;

    public BaltabayAsselCourseService(BaltabayAsselCourseRepository courseRepository,
                                      BaltabayAsselUserRepository userRepository,
                                      BaltabayAsselCourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
    }

    public Page<CourseResponse> findAll(String search, String category, BaltabayAsselCourseLevel level,
                                        Boolean published, Pageable pageable) {
        return courseRepository.searchCourses(blankToNull(search), blankToNull(category), level, published, pageable)
                .map(courseMapper::toDto);
    }

    public CourseResponse findById(Long id) {
        return courseMapper.toDto(getCourse(id));
    }

    public CourseResponse create(CourseRequest request) {
        BaltabayAsselUser instructor = userRepository.findById(request.instructorId())
                .orElseThrow(() -> new BaltabayAsselNotFoundException("Instructor not found"));
        BaltabayAsselCourse course = courseMapper.toEntity(request);
        course.setInstructor(instructor);
        BaltabayAsselCourse saved = courseRepository.save(course);
        log.info("Course created: {}", saved.getTitle());
        return courseMapper.toDto(saved);
    }

    public CourseResponse update(Long id, CourseRequest request) {
        BaltabayAsselCourse course = getCourse(id);
        BaltabayAsselUser instructor = userRepository.findById(request.instructorId())
                .orElseThrow(() -> new BaltabayAsselNotFoundException("Instructor not found"));
        courseMapper.apply(request, course);
        course.setInstructor(instructor);
        return courseMapper.toDto(courseRepository.save(course));
    }

    public void delete(Long id) {
        courseRepository.delete(getCourse(id));
        log.info("Course deleted id={}", id);
    }

    public BaltabayAsselCourse getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new BaltabayAsselNotFoundException("Course not found"));
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}
