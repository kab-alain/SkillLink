package kab.auca.skilllink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kab.auca.skilllink.model.AssessmentSubmission;
import kab.auca.skilllink.service.AssessmentSubmissionService;

@RestController
@RequestMapping("/api/assessment-submissions")
@CrossOrigin("*")
public class AssessmentSubmissionController {

    @Autowired
    private AssessmentSubmissionService assessmentSubmissionService;

    @PostMapping("/create")
    public ResponseEntity<AssessmentSubmission> createSubmission(@RequestBody AssessmentSubmission submission) {
        AssessmentSubmission createdSubmission = assessmentSubmissionService.createSubmission(submission);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubmission);
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<AssessmentSubmission> getSubmissionById(@PathVariable Long submissionId) {
        AssessmentSubmission submission = assessmentSubmissionService.getSubmissionById(submissionId);
        if (submission != null) {
            return ResponseEntity.ok(submission);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<AssessmentSubmission>> getSubmissionsByAssessmentId(@PathVariable Long assessmentId) {
        List<AssessmentSubmission> submissions = assessmentSubmissionService.getSubmissionsByAssessmentId(assessmentId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssessmentSubmission>> getSubmissionsByUserId(@PathVariable Long userId) {
        List<AssessmentSubmission> submissions = assessmentSubmissionService.getSubmissionsByUserId(userId);
        return ResponseEntity.ok(submissions);
    }

    @DeleteMapping("/delete/{submissionId}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long submissionId) {
        assessmentSubmissionService.deleteSubmission(submissionId);
        return ResponseEntity.noContent().build();
    }
}
