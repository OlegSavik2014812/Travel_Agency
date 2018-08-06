package com.epam.core.service;

import com.epam.core.entity.Tour;
import com.epam.core.log.ExecutionTime;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Class describes service-object, the execution of which operate {@link Tour} object
 * implementation of {@link TravelAgencyService}
 */
@Service
public class TourService extends BaseEntityService<Tour> {
    private final Strategy<Tour> strategy;

    /**
     * Instantiates a new Tour service.
     *
     * @param strategy the strategy
     */
    @Autowired
    public TourService(Strategy<Tour> strategy) {
        super(strategy);
        this.strategy = strategy;
        strategy.setParametrizedClass(Tour.class);
    }

    /**
     * Search tours by params list.
     *
     * @param countryName   the country name
     * @param hotelName     the hotel name
     * @param numberOfStars the number of stars
     * @param date          the date
     * @return the list
     */
    @ExecutionTime
    public List<Tour> searchToursByParams(String countryName, String hotelName, String numberOfStars, String date) {
        SearchCriteria searchedCountryName = new SearchCriteria("country.name", ":", countryName);
        SearchCriteria searchedHotelName = new SearchCriteria("hotel.name", ":", hotelName);
        SearchCriteria searchedHotelStars = new SearchCriteria("hotel.numberOfStars", ":", numberOfStars);
        SearchCriteria searched = new SearchCriteria("date", ":", reformStringDate(date));
        return strategy.searchEntity(Arrays.asList(searchedCountryName, searchedHotelName, searchedHotelStars, searched));
    }

    private String reformStringDate(String date) {
        if (!date.isEmpty()) {
            return LocalDate.parse(date).toString();
        } else {
            return date;
        }
    }
}
