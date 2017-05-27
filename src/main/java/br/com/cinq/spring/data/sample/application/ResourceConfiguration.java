package br.com.cinq.spring.data.sample.application;

import br.com.cinq.spring.data.sample.resource.CityResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration() {
        register(CityResource.class);
    }

}