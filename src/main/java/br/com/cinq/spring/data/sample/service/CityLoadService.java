package br.com.cinq.spring.data.sample.service;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityLoadService {

    private static final Logger logger = LoggerFactory.getLogger(CityLoadService.class);

    @Autowired
    private CityRepository repository;

    @Autowired
    private CountryService countryService;

    @Async
    public void saveIfNotExistsByNameIgnoringIds(final List<City> cities) {
        Preconditions.checkNotNull(cities);
        logger.info("Saving cities (ignoring ids): {}", cities);

        cities.forEach(c -> {
            final City actualCity = repository.findFirstByNameIgnoreCase(c.getName());

            if (actualCity == null) {
                c.setId(null);
                c.getCountry().setId(null);
                c.setCountry(countryService.saveIfNotExistsByName(c.getCountry()));
                repository.save(c);
                logger.info("City saved: {}", c);
            } else {
                logger.info("City already exists: {}", actualCity);
            }
        });
    }

}
