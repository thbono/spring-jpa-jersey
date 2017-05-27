package br.com.cinq.spring.data.sample.resource;

import br.com.cinq.spring.data.sample.application.ValidationHelper;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;
import br.com.cinq.spring.data.sample.service.CityLoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cities")
public class CityResource {

    private static final Logger logger = LoggerFactory.getLogger(CityResource.class);

    @Autowired
    private CityRepository repository;

    @Autowired
    private CityLoadService loadService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCities(@QueryParam("country") String countryName) {
        final Iterable<City> cities = StringUtils.isEmpty(countryName)
                ? repository.findAll()
                : repository.findByCountryNameContainingIgnoreCase(countryName);
        return Response.ok(cities).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadCities(@RequestBody List<City> cities) throws InterruptedException {
        if (cities == null || cities.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.info("New request for load cities: {}", cities);

        final List<ValidationHelper.ValidationError> validationErrors = ValidationHelper.validateList(cities);

        if (!validationErrors.isEmpty()) {
            logger.warn("Validation error(s) found: {}", validationErrors);
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors).build();
        }

        loadService.saveIfNotExistsByNameIgnoringIds(cities); // async request

        return Response.accepted().build();
    }

}
