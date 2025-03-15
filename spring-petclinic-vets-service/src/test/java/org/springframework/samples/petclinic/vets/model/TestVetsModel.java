package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetModelTest {

    @Test
    void testVetGetterAndSetter() {
        Vet vet = new Vet();
        vet.setId(1);
        vet.setFirstName("John");
        vet.setLastName("Doe");

        assertEquals(1, vet.getId());
        assertEquals("John", vet.getFirstName());
        assertEquals("Doe", vet.getLastName());
    }

    @Test
    void testSpecialtyGetterAndSetter() {
        Specialty specialty = new Specialty();
        specialty.setName("Dentistry");

        assertEquals("Dentistry", specialty.getName());
    }

    @Test
    void testVetSpecialtyRelationship() {
        Vet vet = new Vet();
        Specialty specialty1 = new Specialty();
        specialty1.setName("Surgery");
        Specialty specialty2 = new Specialty();
        specialty2.setName("Radiology");

        vet.addSpecialty(specialty1);
        vet.addSpecialty(specialty2);

        Set<Specialty> specialties = vet.getSpecialtiesInternal();
        List<Specialty> sortedSpecialties = vet.getSpecialties();

        assertEquals(2, specialties.size());
        assertEquals("Radiology", sortedSpecialties.get(0).getName()); // Check sorted order
        assertEquals("Surgery", sortedSpecialties.get(1).getName());
    }
}
