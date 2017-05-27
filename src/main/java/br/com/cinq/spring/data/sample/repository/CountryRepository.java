package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findAll();

    @Query("select c from Country c where c.name like %?1%")
    List<Country> findLikeName(String name);

    Country findFirstByNameIgnoreCase(String name);

}
