package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetModelTest {

    private Vet vet;
    private Specialty specialty1;
    private Specialty specialty2;

    @BeforeEach
    void setUp() {
        vet = new Vet();
        vet.setId(1);
        vet.setFirstName("John");
        vet.setLastName("Doe");

        specialty1 = new Specialty();
        specialty1.setId(1);
        specialty1.setName("Surgery");

        specialty2 = new Specialty();
        specialty2.setId(2);
        specialty2.setName("Radiology");
    }

    @Test
    void testVetGetterAndSetter() {
        assertEquals(1, vet.getId());
        assertEquals("John", vet.getFirstName());
        assertEquals("Doe", vet.getLastName());

        vet.setId(2);
        vet.setFirstName("Jane");
        vet.setLastName("Smith");

        assertEquals(2, vet.getId());
        assertEquals("Jane", vet.getFirstName());
        assertEquals("Smith", vet.getLastName());
    }

    @Test
    void testSpecialtyGetterAndSetter() {
        Specialty specialty = new Specialty();
        specialty.setId(3);
        specialty.setName("Dentistry");

        assertEquals(3, specialty.getId());
        assertEquals("Dentistry", specialty.getName());
    }

    @Test
    void testVetSpecialtyRelationship() {
        vet.addSpecialty(specialty1);
        vet.addSpecialty(specialty2);

        Set<Specialty> specialties = vet.getSpecialtiesInternal();
        List<Specialty> sortedSpecialties = vet.getSpecialties();

        assertEquals(2, specialties.size());
        assertEquals("Radiology", sortedSpecialties.get(0).getName()); // Check sorted order
        assertEquals("Surgery", sortedSpecialties.get(1).getName());
    }

    @Test
    void testGetNrOfSpecialties() {
        assertEquals(0, vet.getNrOfSpecialties());
        vet.addSpecialty(specialty1);
        assertEquals(1, vet.getNrOfSpecialties());
        vet.addSpecialty(specialty2);
        assertEquals(2, vet.getNrOfSpecialties());
    }

    @Test
    void testEmptySpecialties() {
        assertTrue(vet.getSpecialties().isEmpty());
    }
}
