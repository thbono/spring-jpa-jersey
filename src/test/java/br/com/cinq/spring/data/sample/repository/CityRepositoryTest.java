package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.Application;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
public class CityRepositoryTest {

    @Autowired
    private CityRepository dao;

    @Test
    public void testQueryCity() {
        Assert.assertNotNull(dao);
        
        Assert.assertTrue(dao.count() > 0);

        Country country = new Country();
        country.setId(3); // Should be France

        List<City> list = dao.findByCountry(country);

        Assert.assertEquals(2, list.size());
    }

}
