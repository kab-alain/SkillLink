package kab.auca.skilllink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.AssessmentSubmission;
import kab.auca.skilllink.repository.AssessmentSubmissionRepository;

@Service
public class AssessmentSubmissionService {

    @Autowired
    private AssessmentSubmissionRepository assessmentSubmissionRepository;

    public AssessmentSubmission createSubmission(AssessmentSubmission submission) {
        return assessmentSubmissionRepository.save(submission);
    }

    public AssessmentSubmission getSubmissionById(Long submissionId) {
        return assessmentSubmissionRepository.findById(submissionId).orElse(null);
    }

    public List<AssessmentSubmission> getSubmissionsByAssessmentId(Long assessmentId) {
        return assessmentSubmissionRepository.findByAssessment_AssessmentId(assessmentId);
    }

    public List<AssessmentSubmission> getSubmissionsByUserId(Long userId) {
        return assessmentSubmissionRepository.findByUser_UserId(userId);
    }

    public void deleteSubmission(Long submissionId) {
        assessmentSubmissionRepository.deleteById(submissionId);
    }
}
