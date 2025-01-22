package kab.auca.skilllink.service;

// import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.AssessmentSubmission;
import kab.auca.skilllink.repository.AssessmentSubmissionRepository;

@Service
public class AssessmentSubmissionService {

    @Autowired
    private AssessmentSubmissionRepository assessmentSubmissionRepository;

    public AssessmentSubmission createSubmission(AssessmentSubmission submission) {
        // Check if the assessment ID exists
        Optional<AssessmentSubmission> existingSubmission = assessmentSubmissionRepository
                .findByAssessment_AssessmentId(submission.getAssessment().getAssessmentId());

        if (existingSubmission.isPresent()) {
            // Check the type of the existing assessment
            if (existingSubmission.get().getAssessment().getType()
                    .equalsIgnoreCase(submission.getAssessment().getType())) {
                throw new IllegalArgumentException("A submission for this assessment ID and type already exists");
            }
        }

        // Save and return the new submission
        return assessmentSubmissionRepository.save(submission);
    }

    public AssessmentSubmission getSubmissionById(Long submissionId) {
        return assessmentSubmissionRepository.findById(submissionId).orElse(null);
    }

    public List<AssessmentSubmission> getSubmissionsByAssessmentId(Long assessmentId) {
        return assessmentSubmissionRepository.findAllByAssessment_AssessmentId(assessmentId);
    }

    public List<AssessmentSubmission> getSubmissionsByUserId(Long userId) {
        return assessmentSubmissionRepository.findByUser_UserId(userId);
    }

    public void deleteSubmission(Long submissionId) {
        assessmentSubmissionRepository.deleteById(submissionId);
    }

    public Boolean IsSubmissionLate(AssessmentSubmission submission) {
        Date deadline = submission.getAssessment().getDeadline();
        Date submissionDate = submission.getSubmissionDate();

        return submissionDate.after(deadline);
    }

    public String updateStatus(Long submissionId, int marks) {

        AssessmentSubmission submission = assessmentSubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        // Check if the submission is late
        if (IsSubmissionLate(submission)) {
            submission.setStatus("Late"); // Update status in the entity
            assessmentSubmissionRepository.save(submission); // Save the late status
            return "Late";
        } else {
            submission.setStatus("On Time"); // Update status for on-time submission
            submission.setScore(marks);
            assessmentSubmissionRepository.save(submission); // Save the status
            return "On Time";
        }
    }

}
