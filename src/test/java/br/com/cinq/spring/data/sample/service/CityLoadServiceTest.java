package br.com.cinq.spring.data.sample.service;

import br.com.cinq.spring.data.sample.Application;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
public class CityLoadServiceTest {

    @Autowired
    private CityLoadService service;

    @Test
    public void testSaveIfNotExistsByNameIgnoringIdsWithExistingCity() throws InterruptedException {
        service.saveIfNotExistsByNameIgnoringIds(Lists.newArrayList(new City("Curitiba", new Country("Brazil"))));
        Thread.sleep(2000L);
    }

    @Test
    public void testSaveIfNotExistsByNameIgnoringIds() throws InterruptedException {
        service.saveIfNotExistsByNameIgnoringIds(Lists.newArrayList(new City("Cianorte", new Country("Brazil"))));
        Thread.sleep(2000L);
    }

}