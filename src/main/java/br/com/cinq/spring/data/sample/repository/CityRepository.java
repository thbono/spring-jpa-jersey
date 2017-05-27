package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Integer> {

    List<City> findByCountryNameContainingIgnoreCase(String name);

    List<City> findByCountry(Country country);

    City findFirstByNameIgnoreCase(String name);

}
