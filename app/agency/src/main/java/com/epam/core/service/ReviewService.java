package com.epam.core.service;

import com.epam.core.strategy.Strategy;
import com.epam.core.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class describes service-object, the execution of which operate {@link Review} object
 * implementation of {@link TravelAgencyService}
 */
@Service
public class ReviewService extends BaseEntityService<Review> {
    /**
     * Instantiates a new Review service.
     *
     * @param strategy the strategy
     */
    @Autowired
    public ReviewService(Strategy<Review> strategy) {
        super(strategy);
        strategy.setParametrizedClass(Review.class);
    }
}
