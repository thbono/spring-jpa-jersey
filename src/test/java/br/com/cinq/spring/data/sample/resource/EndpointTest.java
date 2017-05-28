package br.com.cinq.spring.data.sample.resource;

import br.com.cinq.spring.data.sample.Application;
import br.com.cinq.spring.data.sample.application.ValidationHelper;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
public class EndpointTest {

    private final String localhost = "http://localhost:";
    private final String resource = "/rest/cities";

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetCities() throws InterruptedException {
        String country = "France";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + this.resource)
                .queryParam("country", country);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<City[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                entity, City[].class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Thread.sleep(2000L);

        City[] cities = response.getBody();

        Assert.assertEquals(2, cities.length);
    }

    @Test
    public void testGetAllCities() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = getUriComponentsBuilder();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<City[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                entity, City[].class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        City[] cities = response.getBody();
        Assert.assertTrue(cities.length >= 9);
    }

    @Test
    public void testLoadCitiesWithEmptyPayload() {
        HttpHeaders headers = getHttpHeaders();
        UriComponentsBuilder builder = getUriComponentsBuilder();

        HttpEntity<City[]> entity = new HttpEntity<>(new City[]{}, headers);

        ResponseEntity<Void> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                entity, Void.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testLoadCitiesWithValidationError() {
        HttpHeaders headers = getHttpHeaders();
        UriComponentsBuilder builder = getUriComponentsBuilder();

        City[] cities = new City[] {
                new City(new Country("Brazil"))
        };

        HttpEntity<City[]> entity = new HttpEntity<>(cities, headers);

        ResponseEntity<ValidationHelper.ValidationError[]> response =
                this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                entity, ValidationHelper.ValidationError[].class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ValidationHelper.ValidationError[] errors = response.getBody();
        Assert.assertEquals(1, errors.length);
    }

    @Test
    public void testLoadCities() {
        HttpHeaders headers = getHttpHeaders();
        UriComponentsBuilder builder = getUriComponentsBuilder();

        City[] cities = new City[] {
                new City("San Francisco", new Country("United States"))
        };

        HttpEntity<City[]> entity = new HttpEntity<>(cities, headers);

        ResponseEntity<Void> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                entity, Void.class);

        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    private UriComponentsBuilder getUriComponentsBuilder() {
        return UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + this.resource);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
