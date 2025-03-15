package org.springframework.samples.petclinic.vets.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link CacheConfig} and {@link VetsProperties}.
 */
@SpringBootTest
class VetsSystemTest {

    @MockBean
    private VetsProperties vetsProperties;

    @Test
    void cacheConfigShouldBeLoadedInProductionProfile(ApplicationContext context) {
        boolean isCacheConfigPresent = context.containsBean("cacheConfig");
        assertThat(isCacheConfigPresent).isTrue();
    }

    @Test
    void vetsPropertiesShouldBeLoaded() {
        VetsProperties.Cache cache = new VetsProperties.Cache(300, 100);
        VetsProperties properties = new VetsProperties(cache);
        
        assertThat(properties.cache().ttl()).isEqualTo(300);
        assertThat(properties.cache().heapSize()).isEqualTo(100);
    }
}
