package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CountryRepository extends Repository<Country, Integer> {

    List<Country> findAll();

    @Query("select c from Country c where c.name like %?1%")
    List<Country> findLikeName(String name);

    Long count();

}
