package kab.auca.skilllink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kab.auca.skilllink.repository.AssessmentRepository;
import kab.auca.skilllink.repository.CourseRepository;
import kab.auca.skilllink.repository.UserRepository;

@RestController
@RequestMapping("/api/search")
@CrossOrigin("*")
public class searchController {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private AssessmentRepository assessmentRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public ResponseEntity<Map<String, Object>> search(@RequestParam String q) {
    Map<String, Object> results = new HashMap<>();
    results.put("courses", courseRepository.searchByTitleOrDescription(q));
    results.put("assessments", assessmentRepository.searchByTypeOrCourseTitle(q));
    results.put("users", userRepository.searchByName(q));
    return ResponseEntity.ok(results);
  }
}
