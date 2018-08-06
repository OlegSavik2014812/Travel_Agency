package com.epam.core.config;

import com.epam.core.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * The type Entity bean holder.
 */
@Configuration
public class EntityBeanHolder {

    /**
     * Country country.
     *
     * @return the country
     */
    @Bean(name = "country")
    public Country country() {
        return new Country("Belarus");
    }

    /**
     * Hotel hotel.
     *
     * @return the hotel
     */
    @Bean(name = "hotel")
    public Hotel hotel() {
        Hotel hotel = new Hotel();
        hotel.setPhoneNumber("1234567");
        hotel.setNumberOfStars(5);
        hotel.setName("Plaza");
        hotel.setCountry(country());
        return hotel;
    }

    /**
     * Review review.
     *
     * @return the review
     */
    @Bean(name = "review")
    public Review review() {
        Review review = new Review();
        review.setContent("Best Of The best");
        review.setUser(user());
        review.setTour(tour());
        return review;
    }

    /**
     * Tour tour.
     *
     * @return the tour
     */
    @Bean(name = "tour")
    public Tour tour() {
        Tour tour = new Tour();
        tour.setCost(12);
        tour.setType(TourType.EXCURSION);
        tour.setDate(LocalDate.of(2012, 12, 12));
        tour.setDuration(7);
        tour.setHotel(hotel());
        tour.setCountry(country());
        tour.setDescription("Description");
        tour.setImageURI("Image.jpeg");
        return tour;

    }

    /**
     * User user.
     *
     * @return the user
     */
    @Bean(name = "user")
    public User user() {
        User user = new User("Aleh", "Savik");
        user.setRole(UserRole.ADMIN);
        return user;
    }

}
