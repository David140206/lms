package edu.unimagdalena.lms.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class InstructorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false, unique = true)
    private Instructor instructor;

    @Column(nullable = false)
    private String phone;

    @Column(length = 1000)
    private String bio;
}
