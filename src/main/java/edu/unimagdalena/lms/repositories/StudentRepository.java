package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    // Usamos Optional porque un correo debería ser único (puede existir o no)
    Optional<Student> findByEmail(String email);


    // Ideal para hacer un buscador en la web donde escribes parte del nombre
    List<Student> findByFullNameContainingIgnoreCase(String fullName);


    // Buscar todos los estudiantes que tengan un correo de una universidad específica
    @Query("SELECT s FROM Student s WHERE s.email LIKE %:domain")
    List<Student> findStudentsByEmailDomain(@Param("domain") String domain);
}
