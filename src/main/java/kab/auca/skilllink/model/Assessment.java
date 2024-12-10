package kab.auca.skilllink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assessments")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String type; // Quiz, Project, Peer Review
    private Integer maxScore;

    // Getter for assessmentId
    public Long getAssessmentId() {
        return assessmentId;
    }

    // Setter for assessmentId
    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    // Getter for course
    public Course getCourse() {
        return course;
    }

    // Setter for course
    public void setCourse(Course course) {
        this.course = course;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Setter for type
    public void setType(String type) {
        this.type = type;
    }

    // Getter for maxScore
    public Integer getMaxScore() {
        return maxScore;
    }

    // Setter for maxScore
    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }
}
