package kab.auca.skilllink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.Course;
import kab.auca.skilllink.model.User;
import kab.auca.skilllink.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long courseId, Course updatedCourse) {
        return courseRepository.findById(courseId).map(existingCourse -> {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setPrice(updatedCourse.getPrice());
            existingCourse.setLevel(updatedCourse.getLevel());
            existingCourse.setCategory(updatedCourse.getCategory());
            existingCourse.setInstructor(updatedCourse.getInstructor());
            return courseRepository.save(existingCourse);
        }).orElse(null);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<Course> getCoursesByInstructor(User instructor) {
        return courseRepository.findByInstructor(instructor);
    }

    public List<Course> getCoursesByCategory(String categoryName) {
        return courseRepository.findByCategoryName(categoryName);
    }

    public List<Course> getCoursesByLevel(String level) {
        return courseRepository.findByLevel(level);
    }
}
