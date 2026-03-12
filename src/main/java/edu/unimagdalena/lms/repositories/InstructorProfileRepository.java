package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.InstructorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstructorProfileRepository extends JpaRepository<InstructorProfile, UUID> {
}
