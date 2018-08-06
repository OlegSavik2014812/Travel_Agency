package com.epam.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Class describing object for storing information about hotel, implements {@link Entity}
 */
@javax.persistence.Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
@Document
/*@NamedQueries({@NamedQuery(name = "findAll", query = "select h from Hotel h")})*/
public class Hotel implements Entity, Serializable {

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

    @Column(name = "name")
    @NotBlank
    @Length(min = 2, max = 255)
    private String name;

    @Column(name = "phone_number")
    @Length(min = 2, max = 255)
    private String phoneNumber;

    @Column(name = "stars")
    @Min(0)
    @Max(5)
    private int numberOfStars;
    @Version
    private Integer version = 0;
}


