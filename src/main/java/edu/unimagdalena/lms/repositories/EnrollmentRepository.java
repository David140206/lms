package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
}
