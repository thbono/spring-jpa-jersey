package br.com.cinq.spring.data.sample.test;

import br.com.cinq.spring.data.sample.Application;
import br.com.cinq.spring.data.sample.entity.City;
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

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testGetCities() throws InterruptedException {
        String country = "France";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/")
                .queryParam("country", country);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<City[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                entity, City[].class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Thread.sleep(2000L);

        City[] cities = response.getBody();

        Assert.assertEquals(2, cities.length);
    }
}
