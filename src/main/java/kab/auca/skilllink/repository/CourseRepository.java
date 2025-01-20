package kab.auca.skilllink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.Course;
import kab.auca.skilllink.model.User;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByTitleAndLevel(String title, String level);

    List<Course> findByInstructor(User instructor);

    List<Course> findByCategoryName(String categoryName);

    List<Course> findByLevel(String level);

    @Query("SELECT c FROM Course c WHERE c.title LIKE %:query% OR c.description LIKE %:query%")
    List<Course> searchByTitleOrDescription(@Param("query") String query);
}
