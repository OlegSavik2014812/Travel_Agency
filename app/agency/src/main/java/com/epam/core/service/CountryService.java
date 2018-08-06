package com.epam.core.service;

import com.epam.core.entity.Country;
import com.epam.core.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class describes service-object, the execution of which operate {@link Country} object
 * implementation of {@link TravelAgencyService}
 */
@Service
public class CountryService extends BaseEntityService<Country> {
    /**
     * Instantiates a new Country service.
     *
     * @param countryStrategy the country strategy
     */
    @Autowired
    public CountryService(Strategy<Country> countryStrategy) {
        super(countryStrategy);
        countryStrategy.setParametrizedClass(Country.class);
    }
}
