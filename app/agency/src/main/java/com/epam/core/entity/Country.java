package com.epam.core.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Class describing object for storing information about country, implements {@link Entity}
 */
@javax.persistence.Entity
@Table(name = "country")
@Data
@Document(collection = "country")
public class Country implements Entity, Serializable {

    @javax.persistence.Id
    @Id
    @Indexed(direction = IndexDirection.ASCENDING)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Field("_id")
    private Long id;

    @Length(min = 2, max = 255)
    @NotBlank
    private String name;

    @Version
    private Integer version = 0;

    /**
     * Instantiates a new Country.
     */
    public Country() {
    }

    /**
     * Instantiates a new Country.
     *
     * @param name the name
     */
    public Country(String name) {
        this.name = name;
    }

}
