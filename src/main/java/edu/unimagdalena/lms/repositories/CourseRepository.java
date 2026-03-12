package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
