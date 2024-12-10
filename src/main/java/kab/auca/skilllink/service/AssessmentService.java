package kab.auca.skilllink.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.Assessment;
import kab.auca.skilllink.repository.AssessmentRepository;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    public Assessment getAssessmentById(Long assessmentId) {
        return assessmentRepository.findById(assessmentId).orElse(null);
    }

    public List<Assessment> getAssessmentsByCourseId(Long courseId) {
        return assessmentRepository.findByCourse_CourseId(courseId);
    }

    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    public Assessment updateAssessment(Long assessmentId, Assessment updatedAssessment) {
        Assessment existingAssessment = getAssessmentById(assessmentId);
        if (existingAssessment != null) {
            existingAssessment.setType(updatedAssessment.getType());
            existingAssessment.setMaxScore(updatedAssessment.getMaxScore());
            existingAssessment.setCourse(updatedAssessment.getCourse());
            return assessmentRepository.save(existingAssessment);
        }
        return null;
    }

    public boolean deleteAssessment(Long assessmentId) {
        if (assessmentRepository.existsById(assessmentId)) {
            assessmentRepository.deleteById(assessmentId);
            return true;
        }
        return false;
    }
}
