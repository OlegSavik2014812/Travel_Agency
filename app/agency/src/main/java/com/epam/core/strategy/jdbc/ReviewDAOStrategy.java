package com.epam.core.strategy.jdbc;

import com.epam.core.entity.*;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Review dao strategy.
 */
@Component
public class ReviewDAOStrategy extends BaseDaoStrategy<Review> implements Strategy<Review> {
    private static final String SQL_CREATE = "INSERT INTO review(id_tour,id_user,content) VALUES(?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM review WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE review SET id_tour = ?, id_user = ?, content = ? WHERE id = ?";
    private static final String SQL_GET_BY_ID = "SELECT tour.id AS tour_id, " +
            "tour.description AS tour_info, tour.cost  AS tour_cost, " +
            "tour.image_uri AS tour_image," +
            "tour.date  AS tour_date, tour.duration AS tour_duration, tour.tour_type as tour_type," +
            "country.id AS country_tour_id, country.name AS country_tour_name," +
            "hotel.id AS hotel_id, hotel.name AS hotel_name, hotel.phone_number AS hotel_phone, hotel.stars AS hotel_stars," +
            "review.id AS review_id, review.content AS review_content," +
            "user.id AS user_id , user.login AS user_login, user.password AS user_password,user.role as user_role FROM review " +
            "JOIN tour ON tour.id = review.id " +
            "JOIN hotel ON hotel.id = tour.id_hotel " +
            "JOIN user ON user.id  = review.id " +
            "JOIN country ON country.id = tour.id_country " +
            "WHERE review.id = ?";
    private static final String SQL_GET_ALL = "select tour.id as tour_id, " +
            "tour.description as tour_info, tour.cost  as tour_cost, " +
            "tour.image_uri as tour_image," +
            "tour.date  as tour_date, tour.duration as tour_duration, tour.tour_type as tour_type," +
            "country.id as country_tour_id, country.name as country_tour_name," +
            "hotel.id as hotel_id, hotel.name as hotel_name, hotel.phone_number as hotel_phone, hotel.stars as hotel_stars," +
            "review.id as review_id, review.content as review_content," +
            "user.id as user_id , user.login as user_login, user.password as user_password,user.role as user_role from review " +
            "join tour on tour.id = review.id " +
            "join hotel on hotel.id = tour.id_hotel " +
            "join user on user.id  = review.id " +
            "join country on country.id = tour.id_country ";
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Review> rowMapper = (resultSet, i) -> {
        Country country = new Country(resultSet.getString("country_tour_name"));
        country.setId(resultSet.getLong("country_tour_id"));

        Hotel hotel = new Hotel();
        hotel.setName(resultSet.getString("hotel_name"));
        hotel.setNumberOfStars(resultSet.getInt("hotel_stars"));
        hotel.setPhoneNumber(resultSet.getString("hotel_phone"));
        hotel.setCountry(country);
        hotel.setId(resultSet.getLong("hotel_id"));

        Tour tour = new Tour();
        tour.setImageURI(resultSet.getString("tour_image"));
        tour.setCost(resultSet.getDouble("tour_cost"));
        tour.setDescription(resultSet.getString("tour_info"));
        tour.setDuration(resultSet.getInt("tour_duration"));
        tour.setDate(resultSet.getTimestamp("tour_date").toLocalDateTime().toLocalDate());
        tour.setCountry(country);
        tour.setHotel(hotel);
        tour.setType(TourType.fromString("tour_type"));
        tour.setId(resultSet.getLong("tour_id"));

        User user = new User(resultSet.getString("user_login"), resultSet.getString("user_password"));
        user.setId(resultSet.getLong("user_id"));
        user.setRole(UserRole.fromString(resultSet.getString("user_role")));

        Review review = new Review();
        review.setTour(tour);
        review.setUser(user);
        review.setContent(resultSet.getString("review_content"));
        review.setId(resultSet.getLong("review_id"));
        return review;
    };

    /**
     * Instantiates a new Review dao strategy.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired(required = false)
    public ReviewDAOStrategy(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName(Review.class.getSimpleName().toLowerCase());
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Review entity) {
        jdbcTemplate.update(SQL_CREATE, entity.getTour().getId(), entity.getUser().getId(), entity.getContent());
    }

    @Override
    public Review findById(Long id) {
        return jdbcTemplate.query(SQL_GET_BY_ID, rowMapper, id).get(0);
    }

    @Override
    public Review update(Review entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getTour().getId(), entity.getUser().getId(), entity.getContent(), entity.getId());
        return findById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE, id) == 1;
    }

    @Override
    public List<Review> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, rowMapper);
    }

    @Override
    public List<Review> searchEntity(List<SearchCriteria> params) {
        return null;
    }

    @Override
    public void setParametrizedClass(Class<Review> c) {

    }
}
