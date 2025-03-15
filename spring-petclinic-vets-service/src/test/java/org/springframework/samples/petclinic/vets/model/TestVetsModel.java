package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the Vet and Specialty model classes
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class VetModelTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VetRepository vetRepository;

    @Test
    void shouldCreateAndFindVet() {
        // Create and persist a vet
        Vet vet = new Vet();
        vet.setFirstName("John");
        vet.setLastName("Doe");
        entityManager.persistAndFlush(vet);

        // Find the vet by ID
        Vet foundVet = vetRepository.findById(vet.getId()).orElse(null);
        
        // Verify the vet was found and has correct properties
        assertThat(foundVet).isNotNull();
        assertThat(foundVet.getFirstName()).isEqualTo("John");
        assertThat(foundVet.getLastName()).isEqualTo("Doe");
        assertThat(foundVet.getNrOfSpecialties()).isEqualTo(0);
    }

    @Test
    void shouldAddSpecialtiesToVet() {
        // Create a specialty
        Specialty specialty1 = new Specialty();
        specialty1.setName("Dentistry");
        entityManager.persistAndFlush(specialty1);

        Specialty specialty2 = new Specialty();
        specialty2.setName("Surgery");
        entityManager.persistAndFlush(specialty2);

        // Create a vet and add specialties
        Vet vet = new Vet();
        vet.setFirstName("Jane");
        vet.setLastName("Smith");
        vet.addSpecialty(specialty1);
        vet.addSpecialty(specialty2);
        entityManager.persistAndFlush(vet);

        // Find the vet
        Vet foundVet = vetRepository.findById(vet.getId()).orElse(null);
        
        // Verify specialties
        assertThat(foundVet).isNotNull();
        assertThat(foundVet.getNrOfSpecialties()).isEqualTo(2);
        
        List<Specialty> specialties = foundVet.getSpecialties();
        assertThat(specialties).hasSize(2);
        
        // Verify specialties are sorted by name (as implemented in getSpecialties())
        assertThat(specialties.get(0).getName()).isEqualTo("Dentistry");
        assertThat(specialties.get(1).getName()).isEqualTo("Surgery");
    }

    @Test
    void shouldGetEmptySpecialtiesListWhenNoneAdded() {
        Vet vet = new Vet();
        vet.setFirstName("Mark");
        vet.setLastName("Johnson");
        entityManager.persistAndFlush(vet);

        Vet foundVet = vetRepository.findById(vet.getId()).orElse(null);
        
        assertThat(foundVet).isNotNull();
        assertThat(foundVet.getSpecialties()).isNotNull();
        assertThat(foundVet.getSpecialties()).isEmpty();
        assertThat(foundVet.getNrOfSpecialties()).isEqualTo(0);
    }

    @Test
    void shouldUpdateVetDetails() {
        // Create initial vet
        Vet vet = new Vet();
        vet.setFirstName("Original");
        vet.setLastName("Name");
        entityManager.persistAndFlush(vet);
        
        // Update vet
        Integer vetId = vet.getId();
        Vet foundVet = vetRepository.findById(vetId).orElse(null);
        assertThat(foundVet).isNotNull();
        
        foundVet.setFirstName("Updated");
        foundVet.setLastName("Person");
        entityManager.persistAndFlush(foundVet);
        
        // Verify updates
        Vet updatedVet = vetRepository.findById(vetId).orElse(null);
        assertThat(updatedVet).isNotNull();
        assertThat(updatedVet.getFirstName()).isEqualTo("Updated");
        assertThat(updatedVet.getLastName()).isEqualTo("Person");
    }
}
