package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CityRepository extends Repository<City, Long> {

    List<City> findAll();

    List<City> findByCountryNameContainingIgnoreCase(String name);

    List<City> findByCountry(Country country);

    Long count();

}
