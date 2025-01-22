package kab.auca.skilllink.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/assessment-submissions")
@CrossOrigin("*")
public class AssessmentSubmissionController {

    @Autowired
    private AssessmentSubmissionService assessmentSubmissionService;

    @PostMapping("/create")
    public ResponseEntity<?> createSubmission(@RequestBody AssessmentSubmission submission) {
        try {
            // Attempt to create the submission
            AssessmentSubmission createdSubmission = assessmentSubmissionService.createSubmission(submission);

            // Return success response with created resource
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubmission);
        } catch (RuntimeException ex) {
            // Handle the runtime exception (e.g., duplicate submission ID)
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception ex) {
            // Handle generic exceptions
            // Handle runtime exceptions (e.g., duplicate submission ID)
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
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

    // Additional methods for checking submission status and updating submission
    @PutMapping("update/{id}")
    public ResponseEntity<String> UpdateStatusAndMarks(@PathVariable Long id,
            @RequestBody Map<String, Integer> requestBody) {
        // Extract the status from the request body
        Integer status = requestBody.get("status");

        // Validate inputs
        if (status == null) {
            return ResponseEntity.badRequest().body("Status is required");
        }
        // Update the status
        String updatedStatus = assessmentSubmissionService.updateStatus(id, status);

        // Return the appropriate response
        if ("Late".equalsIgnoreCase(updatedStatus)) {
            return ResponseEntity.ok("Late");
        } else {

            return ResponseEntity.ok("On Time");
        }
    }

}
