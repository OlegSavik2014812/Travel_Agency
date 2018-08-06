package com.epam.web.controller;

import com.epam.core.entity.Tour;
import com.epam.core.entity.User;
import com.epam.core.service.TourService;
import com.epam.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Tour controller.
 */
@Controller
public class TourController {
    private static final String HOME_PAGE = "tour_list";
    private static final String USER_TOUR_LIST_PAGE = "user_tour_list";
    private static final String TOUR_LIST_ATTRIBUTE = "tourList";
    private final TourService tourService;
    private final UserService userService;

    /**
     * Instantiates a new Tour controller.
     *
     * @param tourService the tourService
     */
    @Autowired
    public TourController(TourService tourService, UserService userService) {
        this.tourService = tourService;
        this.userService = userService;
    }

    /**
     * Gets tour list.
     *
     * @param request the request
     * @return the tour list
     */
    @GetMapping(value = "/")
    public String getTourList(HttpServletRequest request) {
        List<Tour> tours = tourService.getList();
        request.getSession().setAttribute(TOUR_LIST_ATTRIBUTE, tours);
        return HOME_PAGE;
    }

    /**
     * Gets criteria tour list.
     *
     * @param countryName the country name
     * @param hotelName   the hotel name
     * @param stars       the stars
     * @param date        the date
     * @param model       the model
     * @return the criteria tour list
     */
    @GetMapping(value = "/search")
    public String getCriteriaTourList(@RequestParam(value = "countryName", required = false) String countryName,
                                      @RequestParam(value = "hotelName", required = false) String hotelName,
                                      @RequestParam(value = "numberOfStars", required = false) String stars,
                                      @RequestParam(value = "date", required = false) String date, Model model) {
        if (!hasAllParameters(countryName, hotelName, stars, date)) {
            return HOME_PAGE;
        }
        List<Tour> tours = tourService.searchToursByParams(countryName, hotelName, stars, date);
        model.addAttribute(TOUR_LIST_ATTRIBUTE, tours);
        return HOME_PAGE;
    }

    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping(value = "/order_tour{id}")
    public String orderTour(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Tour tour = tourService.getById(id);
        user.addTour(tour);
        userService.update(user);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping(value = "/remove_tour{id}")
    public String removeUserTour(@PathVariable("id") int id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user.removeTour(id);
        userService.update(user);
        return "redirect:/user_tracks";
    }


    @GetMapping(value = "/user_tracks")
    public String showUserTracks(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("userTours", user.getTourList());
        return USER_TOUR_LIST_PAGE;
    }


    /**
     * Test exception.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/test")
    public void testException() {
        throw new RuntimeException();
    }

    private boolean hasAllParameters(String... params) {
        for (String param : params) {
            if (param == null) {
                return false;
            }
        }
        return true;
    }
}
