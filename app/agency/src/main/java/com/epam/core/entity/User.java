package com.epam.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 */
@javax.persistence.Entity
@Table(name = "user")
@Getter
@Setter
@Document
/*@NamedQueries({@NamedQuery(name = "findAll", query = "select u from User u")})*/
public class User implements Entity, Serializable {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field("_id")
    private Long id;

    @Column(name = "login")
    @NotBlank
    @Length(min = 2, max = 255)
    @Field("login")
    private String login;

    @Column(name = "password")
    @NotBlank
    @Length(min = 2, max = 255)
    @Field("password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Tour> tourList;
    @Version
    private Integer version = 0;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * Instantiates a new User.
     */
    public User() {
        tourList = new ArrayList<>();
    }

    public void addTour(Tour tour) {
        tourList.add(tour);
    }

    public void removeTour(int index) {
        tourList.remove(index);
    }

    /**
     * Instantiates a new User.
     *
     * @param login    the login
     * @param password the password
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        tourList = new ArrayList<>();
    }
}
