package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}
