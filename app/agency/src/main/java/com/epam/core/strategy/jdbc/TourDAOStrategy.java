package com.epam.core.strategy.jdbc;

import com.epam.core.entity.Country;
import com.epam.core.entity.Hotel;
import com.epam.core.entity.Tour;
import com.epam.core.entity.TourType;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * The type Tour dao strategy.
 */
@Component
public class TourDAOStrategy extends BaseDaoStrategy<Tour> implements Strategy<Tour> {
    private static final String SQL_CREATE = "INSERT INTO tour(description, cost, date, duration, id_country, id_hotel,tour_type) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM tour WHERE id =?";
    private static final String SQL_UPDATE = "UPDATE tour SET description = ?, cost = ?, date = ?, duration = ?, " +
            "id_country = ?, id_hotel = ?, tour_type = ? WHERE id = ?";
    private static final String SQL_GET_BY_ID = "SELECT tour.id AS tour_id, " +
            "tour.description AS tour_info, tour.cost  AS tour_cost, tour.tour_type as tour_type," +
            "tour.image_uri AS tour_image," +
            "tour.date  AS tour_date, tour.duration AS tour_duration, " +
            "country.id AS country_tour_id, country.name AS country_tour_name, " +
            "hotel.id AS hotel_id, hotel.name AS hotel_name, hotel.phone_number AS hotel_phone, hotel.stars AS hotel_stars " +
            "FROM tour " +
            "JOIN hotel ON hotel.id = tour.id_hotel " +
            "JOIN country ON country.id = tour.id_country " +
            "WHERE tour.id = ?";
    private static final String SQL_GET_ALL = "SELECT tour.id AS tour_id, " +
            "tour.description AS tour_info, tour.cost  AS tour_cost, tour.tour_type as tour_type" +
            "tour.image_uri AS tour_image," +
            "tour.date  AS tour_date, tour.duration AS tour_duration,tour.tour_type as tour_type," +
            "country.id AS country_tour_id, country.name AS country_tour_name, " +
            "hotel.id AS hotel_id, hotel.name AS hotel_name, hotel.phone_number AS hotel_phone, hotel.stars AS hotel_stars " +
            "FROM tour " +
            "JOIN hotel ON hotel.id = tour.id_hotel " +
            "JOIN country ON country.id = tour.id_country ";
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Tour> rowMapper = (resultSet, i) -> {
        Country country = new Country(resultSet.getString("country_tour_name"));
        country.setId(resultSet.getLong("country_tour_id"));

        Hotel hotel = new Hotel();
        hotel.setName(resultSet.getString("hotel_name"));
        hotel.setNumberOfStars(resultSet.getInt("hotel_stars"));
        hotel.setPhoneNumber(resultSet.getString("hotel_phone"));
        hotel.setCountry(country);
        hotel.setId(resultSet.getLong("hotel_id"));

        Timestamp time = resultSet.getTimestamp("tour_date");
        Tour tour = new Tour();
        tour.setImageURI(resultSet.getString("tour_image"));
        tour.setCost(resultSet.getDouble("tour_cost"));
        tour.setDescription(resultSet.getString("tour_info"));
        tour.setDuration(resultSet.getInt("tour_duration"));
        tour.setDate(time.toLocalDateTime().toLocalDate());
        tour.setCountry(country);
        tour.setHotel(hotel);
        tour.setType(TourType.fromString(resultSet.getString("tour_type")));
        tour.setId(resultSet.getLong("tour_id"));
        return tour;
    };

    /**
     * Instantiates a new Tour dao strategy.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired(required = false)
    public TourDAOStrategy(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName(Tour.class.getSimpleName().toLowerCase());
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Tour entity) {
        LocalDateTime dateTime = LocalDateTime.of(entity.getDate(), LocalTime.now());
        jdbcTemplate.update(SQL_CREATE,
                entity.getDescription(),
                entity.getCost(),
                Timestamp.valueOf(dateTime),
                entity.getDuration(),
                entity.getCountry().getId(),
                entity.getHotel().getId(),
                entity.getType());
    }

    @Override
    public Tour findById(Long id) {
        return jdbcTemplate.query(SQL_GET_BY_ID, rowMapper, id).get(0);
    }

    @Override
    public Tour update(Tour entity) {
        LocalDateTime dateTime = LocalDateTime.of(entity.getDate(), LocalTime.now());
        jdbcTemplate.update(SQL_UPDATE,
                entity.getDescription(),
                entity.getCost(),
                Timestamp.valueOf(dateTime),
                entity.getDuration(),
                entity.getCountry().getId(),
                entity.getHotel().getId(),
                entity.getType(),
                entity.getId());
        return findById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE, id) == 1;
    }

    @Override
    public List<Tour> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, rowMapper);
    }

    @Override
    public List<Tour> searchEntity(List<SearchCriteria> params) {
        return null;
    }

    @Override
    public void setParametrizedClass(Class<Tour> c) {

    }
}
