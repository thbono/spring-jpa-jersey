package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.Application;
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
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository dao;

    @Test
    public void testGelAllCountries() {
        Assert.assertNotNull(dao);

        long count = dao.count();

        Assert.assertTrue(count > 0);

        List<Country> countries = dao.findAll();

        Assert.assertEquals((int) count, countries.size());
    }

    @Test
    public void testFindOneCountry() {
        Assert.assertNotNull(dao);

        List<Country> countries = dao.findLikeName("Fra");

        Assert.assertEquals(1, countries.size());
    }

}
