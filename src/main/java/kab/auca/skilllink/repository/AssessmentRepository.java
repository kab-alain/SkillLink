package kab.auca.skilllink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.Assessment;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByCourse_CourseId(Long courseId);

    @Query("SELECT a FROM Assessment a WHERE a.type LIKE %:query% OR a.course.title LIKE %:query%")
    List<Assessment> searchByTypeOrCourseTitle(@Param("query") String query);

}
