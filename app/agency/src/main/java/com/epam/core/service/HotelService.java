package com.epam.core.service;

import com.epam.core.strategy.Strategy;
import com.epam.core.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class describes service-object, the execution of which operate {@link Hotel} object
 * implementation of {@link TravelAgencyService}
 */
@Service
public class HotelService extends BaseEntityService<Hotel> {
    /**
     * Instantiates a new Hotel service.
     *
     * @param strategy the strategy
     */
    @Autowired
    public HotelService(Strategy<Hotel> strategy) {
        super(strategy);
        strategy.setParametrizedClass(Hotel.class);
    }
}
