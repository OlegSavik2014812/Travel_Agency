package com.epam.core.strategy.jdbc;

import com.epam.core.entity.Country;
import com.epam.core.entity.Entity;
import com.epam.core.entity.Hotel;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Hotel dao strategy.
 */
@Component
public class HotelDAOStrategy extends BaseDaoStrategy<Hotel> implements Strategy<Hotel> {
    private static final String SQL_CREATE = "INSERT INTO hotel(name,phone_number,stars,id_country) VALUES (?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM hotel WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE hotel SET name = ?, phone_number = ?, stars = ?, id_country = ? WHERE id = ?";
    private static final String SQL_GET_BY_ID = "SELECT hotel.id AS hotel_id, hotel.name AS hotel_name, " +
            "hotel.phone_number AS hotel_number, hotel.stars AS hotel_stars, " +
            "country.id AS country_id, " +
            "country.name AS country_name FROM hotel " +
            "JOIN country ON country.id = hotel.id WHERE hotel.id = ?";
    private static final String SQL_GEL_ALL = "SELECT hotel.id AS hotel_id, hotel.name AS hotel_name, " +
            "hotel.phone_number AS hotel_number, hotel.stars AS hotel_stars, " +
            "country.id AS country_id, " +
            "country.name AS country_name FROM hotel " +
            "JOIN country ON country.id = hotel.id";
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Hotel> rowMapper = (resultSet, i) -> {

        Country country = new Country(resultSet.getString("country_name"));
        country.setId(resultSet.getLong("country_id"));
        Hotel hotel = new Hotel();
        hotel.setName(resultSet.getString("hotel_name"));
        hotel.setPhoneNumber(resultSet.getString("hotel_number"));
        hotel.setNumberOfStars(resultSet.getInt("hotel_stars"));
        hotel.setCountry(country);
        hotel.setId(resultSet.getLong("hotel_id"));
        return hotel;

    };

    /**
     * Instantiates a new Hotel dao strategy.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired(required = false)
    public HotelDAOStrategy(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName(Hotel.class.getSimpleName().toLowerCase());
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Hotel entity) {
        jdbcTemplate.update(SQL_CREATE, entity.getName(), entity.getPhoneNumber(), entity.getNumberOfStars(), entity.getCountry().getId());
    }

    @Override
    public Hotel findById(Long id) {
        return jdbcTemplate.query(SQL_GET_BY_ID, rowMapper, id).get(0);
    }

    @Override
    public Hotel update(Hotel entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getName(), entity.getPhoneNumber(), entity.getNumberOfStars(), entity.getCountry().getId(), entity.getId());
        return findById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE, id) == 1;
    }

    @Override
    public List<Hotel> findAll() {
        return jdbcTemplate.query(SQL_GEL_ALL, rowMapper);
    }

    @Override
    public List<Hotel> searchEntity(List<SearchCriteria> params) {
        return null;
    }

    @Override
    public void setParametrizedClass(Class<Hotel> c) {

    }
}
