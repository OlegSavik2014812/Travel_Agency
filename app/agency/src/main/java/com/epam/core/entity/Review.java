package com.epam.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Class describing object for storing information about review, implements {@link Entity}
 */
@javax.persistence.Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@Document
/*@NamedQueries({@NamedQuery(name = "findAll", query = "select r from Review r")})*/
public class Review implements Entity, Serializable {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Field("_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tour")
    @NotNull
    private Tour tour;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @NotNull
    private User user;

    @Column(name = "content")
    @Length(min = 1, max = 2000)
    private String content;
    @Version
    private Integer version = 0;

}
