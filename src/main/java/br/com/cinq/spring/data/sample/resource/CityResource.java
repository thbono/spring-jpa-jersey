package br.com.cinq.spring.data.sample.resource;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class CityResource {

    @Autowired
    private CityRepository repository;

    @GET
    @Path("/cities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCities(@QueryParam("country") String countryName) {
        final List<City> cities = StringUtils.isEmpty(countryName)
                ? repository.findAll()
                : repository.findByCountryNameContainingIgnoreCase(countryName);
        return Response.ok(cities).build();
    }

}
