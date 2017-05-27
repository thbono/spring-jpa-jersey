package br.com.cinq.spring.data.sample.service;

import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CountryRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public Country saveIfNotExistsByName(final Country country) {
        Preconditions.checkNotNull(country);

        final Country actualCountry = repository.findFirstByNameIgnoreCase(country.getName());

        return actualCountry != null
                ? actualCountry
                : repository.save(country);
    }

}
