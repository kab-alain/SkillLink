package kab.auca.skilllink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.AssessmentSubmission;

@Repository
public interface AssessmentSubmissionRepository extends JpaRepository<AssessmentSubmission, Long> {
    List<AssessmentSubmission> findByUser_UserId(Long userId);  // Corrected method name
    List<AssessmentSubmission> findByAssessment_AssessmentId(Long assessmentId);  // Similarly adjust for AssessmentId
}
