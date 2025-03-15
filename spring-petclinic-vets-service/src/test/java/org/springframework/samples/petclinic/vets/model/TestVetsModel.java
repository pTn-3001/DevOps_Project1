package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Vet} and {@link Specialty} model classes.
 */
class VetModelTest {

    private Vet vet;
    private Specialty specialty;

    @BeforeEach
    void setUp() {
        vet = new Vet();
        vet.setId(1);
        vet.setFirstName("John");
        vet.setLastName("Doe");

        specialty = new Specialty();
        specialty.setName("Dentistry");
    }

    @Test
    void testVetProperties() {
        assertThat(vet.getId()).isEqualTo(1);
        assertThat(vet.getFirstName()).isEqualTo("John");
        assertThat(vet.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testSpecialtyProperties() {
        assertThat(specialty.getName()).isEqualTo("Dentistry");
    }

    @Test
    void testAddSpecialty() {
        vet.addSpecialty(specialty);
        Set<Specialty> specialties = vet.getSpecialtiesInternal();
        assertThat(specialties).hasSize(1);
        assertThat(specialties.iterator().next().getName()).isEqualTo("Dentistry");
    }

    @Test
    void testGetNrOfSpecialties() {
        assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
        vet.addSpecialty(specialty);
        assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
    }
}
