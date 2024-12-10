package kab.auca.skilllink.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.Enrollment;
import kab.auca.skilllink.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Enroll a user in a course
    public Enrollment enrollUser(Enrollment enrollment) {
        enrollment.setEnrollmentDate(LocalDate.now()); // Set current date for enrollment
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
