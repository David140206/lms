package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    // 1. Configuración de Testcontainers para levantar PostgreSQL en Docker
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("lms_db_test")
            .withUsername("test_user")
            .withPassword("test_pass");

    // 2. Inyección de propiedades dinámicas al entorno de Spring
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private StudentRepository studentRepository;

    private Student testStudent;

    // 3. Preparación del entorno antes de cada prueba
    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();

        testStudent = new Student();
        testStudent.setEmail("estudiante.test@unimagdalena.edu.co");
        testStudent.setFullName("Estudiante de Prueba");
        testStudent.setCreatedAt(Instant.now());
        testStudent.setUpdatedAt(Instant.now());
    }

    // 4. Implementación del CRUD

    @Test
    void testCreateStudent() {
        // Act (Ejecutar guardado)
        Student guardado = studentRepository.save(testStudent);

        // Assert (Verificar creación)
        assertNotNull(guardado.getId(), "El ID debe generarse automáticamente al guardar");
        assertEquals("Estudiante de Prueba", guardado.getFullName());
    }

    @Test
    void testReadStudentById() {
        // Arrangee
        Student guardado = studentRepository.save(testStudent);

        // Act (Ejecutar lectura)
        Optional<Student> encontrado = studentRepository.findById(guardado.getId());

        // Assert (Verificar recuperación)
        assertTrue(encontrado.isPresent(), "El estudiante debe existir en la base de datos");
        assertEquals("estudiante.test@unimagdalena.edu.co", encontrado.get().getEmail());
    }

    @Test
    void testUpdateStudent() {
        // Arrange
        Student guardado = studentRepository.save(testStudent);

        // Act (Ejecutar actualización)
        guardado.setFullName("Estudiante Actualizado");
        studentRepository.save(guardado);

        Optional<Student> actualizado = studentRepository.findById(guardado.getId());

        // Assert (Verificar modificación)
        assertTrue(actualizado.isPresent());
        assertEquals("Estudiante Actualizado", actualizado.get().getFullName());
    }

    @Test
    void testDeleteStudent() {
        // Arrange
        Student guardado = studentRepository.save(testStudent);

        // Act (Ejecutar borrado)
        studentRepository.deleteById(guardado.getId());
        Optional<Student> eliminado = studentRepository.findById(guardado.getId());

        // Assert (Verificar eliminación)
        assertFalse(eliminado.isPresent(), "El estudiante ya no debe existir tras ser eliminado");
    }

    @Test
    void debeBuscarEstudiantePorEmail() {
        // Arrange: Guardamos el estudiante de prueba
        studentRepository.save(testStudent);

        // Act: Usamos tu nueva consulta personalizada
        Optional<Student> encontrado = studentRepository.findByEmail("estudiante.test@unimagdalena.edu.co");

        // Assert: Verificamos que lo encuentre correctamente
        assertTrue(encontrado.isPresent());
        assertEquals("Estudiante de Prueba", encontrado.get().getFullName());
    }

    @Test
    void debeBuscarPorNombreIgnorandoMayusculas() {
        // Arrange: Guardamos el estudiante
        studentRepository.save(testStudent);

        // Act: Buscamos solo por una parte del nombre y en minúsculas ("prueba")
        List<Student> resultados = studentRepository.findByFullNameContainingIgnoreCase("prueba");

        // Assert: Verificamos que la lista no esté vacía y contenga al estudiante
        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
        assertTrue(resultados.get(0).getFullName().contains("Prueba"));
    }
}