package br.com.cinq.spring.data.sample.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(max = DataSize.DEFAULT_NAME)
    private String name;

    @NotNull
    @Valid
    @ManyToOne
    private Country country;

    public City() {
    }

    public City(final String name, final Country country) {
        this.name = Preconditions.checkNotNull(name);
        this.country = Preconditions.checkNotNull(country);
    }

    public City(final Country country) {
        this.country = Preconditions.checkNotNull(country);
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = Preconditions.checkNotNull(country);
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("country", country)
                .toString();
    }

}