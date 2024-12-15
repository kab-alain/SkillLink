package kab.auca.skilllink.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.Course;
import kab.auca.skilllink.model.Enrollment;
import kab.auca.skilllink.model.User;
import kab.auca.skilllink.repository.CourseRepository;
import kab.auca.skilllink.repository.EnrollmentRepository;
import kab.auca.skilllink.repository.UserRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    // Enroll a user in a course
    public Enrollment enrollUser(Enrollment enrollment) {
        // Fetch full User from DB using the userId given in the enrollment's user
        // object
        User fetchedUser = userRepository.findById(enrollment.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch full Course from DB using the courseId given in the enrollment's course
        // object
        Course fetchedCourse = courseRepository.findById(enrollment.getCourse().getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Set the fetched entities on the enrollment
        enrollment.setUser(fetchedUser);
        enrollment.setCourse(fetchedCourse);
        enrollment.setEnrollmentDate(LocalDate.now());

        return enrollmentRepository.save(enrollment);
    }

    // Get all enrollments for a user
    public List<Enrollment> getEnrollmentsByUser(Long userId) {
        return enrollmentRepository.findByUserUserId(userId);
    }

    // Get all enrollments for a course
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseCourseId(courseId);
    }

    // Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Delete an enrollment by ID
    public void deleteEnrollment(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }
}
