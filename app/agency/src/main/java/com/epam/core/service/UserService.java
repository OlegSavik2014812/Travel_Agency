package com.epam.core.service;

import com.epam.core.entity.User;
import com.epam.core.log.ExecutionTime;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class describes service-object, the execution of which operate {@link User} object
 * implementation of {@link TravelAgencyService}
 */
@Service
public class UserService extends BaseEntityService<User> {
    private final Strategy<User> strategy;
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    /**
     * Instantiates a new User service.
     *
     * @param strategy the strategy
     */
    @Autowired
    public UserService(Strategy<User> strategy) {
        super(strategy);
        this.strategy = strategy;
        strategy.setParametrizedClass(User.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @ExecutionTime
    @Override
    public boolean add(User entity) {
        Optional<User> entityOptional = Optional.ofNullable(entity);
        try {
            User entityNotNull = entityOptional.orElseThrow(() -> new Exception("Entity is null"));
            if (getUserByLogin(entityNotNull.getLogin()) != null) {
                LOGGER.info("User with login " + entityNotNull.getLogin() + " already exist");
                return false;
            }
            strategy.save(entityNotNull);
            LOGGER.info("Entity  has been created");
            return true;
        } catch (Exception e) {
            LOGGER.warn("Can't save new entity" + e);
            return false;
        }
    }

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the user by login
     */
    @ExecutionTime
    public User getUserByLogin(String login) {
        SearchCriteria searchCriteriaLogin = new SearchCriteria("login", ":", login);
        List<User> users = strategy.searchEntity(Collections.singletonList(searchCriteriaLogin));
        return getUser(users);
    }

    /**
     * Gets user by login and password.
     *
     * @param login    the login
     * @param password the password
     * @return the user by login and password
     */
    @ExecutionTime
    public User getUserByLoginAndPassword(String login, String password) {
        SearchCriteria searchCriteriaLogin = new SearchCriteria("login", ":", login);
        SearchCriteria searchCriteriaPassword = new SearchCriteria("password", ":", password);
        List<User> users = strategy.searchEntity(Arrays.asList(searchCriteriaLogin, searchCriteriaPassword));
        return getUser(users);
    }

    private User getUser(List<User> users) {
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            LOGGER.warn("Can't find user");
            return null;
        }
    }
}
