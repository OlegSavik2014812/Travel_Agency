package com.epam.core.entity;

import com.epam.core.util.JsonLocalDateDeserializer;
import com.epam.core.util.LocalDateConverter;
import com.epam.core.util.TourTypeConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class describing object for storing information about tour, implements {@link Entity}
 */
@javax.persistence.Entity
@Table(name = "tour")
@Getter
@Setter
@NoArgsConstructor
@Document
/*@NamedQueries({@NamedQuery(name = "findAll", query = "select t from Tour t")})*/
public class Tour implements Entity, Serializable {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Field("_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_country")
    @NotNull
    private Country country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_hotel")
    @NotNull
    private Hotel hotel;

    @Column(name = "image_uri")
    private String imageURI;

    @JsonDeserialize(using = JsonLocalDateDeserializer.class)
    @Convert(converter = LocalDateConverter.class)
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "duration")
    @Min(1)
    @Max(31)
    private int duration;

    @Column(name = "tour_type")
    @Convert(converter = TourTypeConverter.class)
    private TourType type;

    @Column(name = "description")
    @Length(min = 2, max = 2000)
    private String description;

    @Column(name = "cost")
    @Digits(integer = 6, fraction = 2)
    private double cost;
    @Version
    private Integer version = 0;

}
