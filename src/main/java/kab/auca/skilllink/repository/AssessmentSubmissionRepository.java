package kab.auca.skilllink.repository;

// import java.time.LocalDateTime;
// import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.AssessmentSubmission;

@Repository
public interface AssessmentSubmissionRepository extends JpaRepository<AssessmentSubmission, Long> {
    List<AssessmentSubmission> findByUser_UserId(Long userId); // Corrected method name

    List<AssessmentSubmission> findAllByAssessment_AssessmentId(Long assessmentId);

    Optional<AssessmentSubmission> findByAssessment_AssessmentId(Long assessmentId);
    // Boolean existsByAssessment_IdAndSubmissionDateAfter(Long assessmentId,
    // LocalDateTime submissionDate);

}
