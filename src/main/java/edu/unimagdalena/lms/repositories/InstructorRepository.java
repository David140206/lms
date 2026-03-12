package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstructorRepository extends JpaRepository<Instructor, UUID> {

    List<Instructor> findInstructorByFullName(String fullName);
}
