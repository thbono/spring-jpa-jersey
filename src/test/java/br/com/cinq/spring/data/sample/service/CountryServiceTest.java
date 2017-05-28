package br.com.cinq.spring.data.sample.service;

import br.com.cinq.spring.data.sample.Application;
import br.com.cinq.spring.data.sample.entity.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
public class CountryServiceTest {

    @Autowired
    private CountryService service;

    @Test
    public void testSaveIfNotExistsByNameWithExistingCountry() {
        service.saveIfNotExistsByName(new Country("Brazil"));
    }

    @Test
    public void testSaveIfNotExistsByName() {
        service.saveIfNotExistsByName(new Country("Italy"));
    }

}