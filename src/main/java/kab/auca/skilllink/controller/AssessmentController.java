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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kab.auca.skilllink.model.Assessment;
import kab.auca.skilllink.service.AssessmentService;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin("*")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping("/create")
    public ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
        Assessment createdAssessment = assessmentService.createAssessment(assessment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessment);
    }

    @GetMapping("/{assessmentId}")
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable Long assessmentId) {
        Assessment assessment = assessmentService.getAssessmentById(assessmentId);
        if (assessment != null) {
            return ResponseEntity.ok(assessment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assessment>> getAssessmentsByCourseId(@PathVariable Long courseId) {
        List<Assessment> assessments = assessmentService.getAssessmentsByCourseId(courseId);
        return ResponseEntity.ok(assessments);
    }

    @GetMapping
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        List<Assessment> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.ok(assessments);
    }

    @PutMapping("/update/{assessmentId}")
    public ResponseEntity<Assessment> updateAssessment(
            @PathVariable Long assessmentId,
            @RequestBody Assessment updatedAssessment) {
        Assessment updated = assessmentService.updateAssessment(assessmentId, updatedAssessment);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{assessmentId}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long assessmentId) {
        boolean deleted = assessmentService.deleteAssessment(assessmentId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
