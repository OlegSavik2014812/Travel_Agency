package com.epam.web.controller;

import com.epam.core.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Hotel controller.
 */
@Controller
public class HotelController {
    private static final String HOTEL_LIST_PAGE = "hotel_list";
    private static final String HOTEL_LIST_ATTRIBUTE = "hotelList";
    private final HotelService service;

    /**
     * Instantiates a new Hotel controller.
     *
     * @param service the service
     */
    @Autowired
    public HotelController(HotelService service) {
        this.service = service;
    }

    /**
     * Gets hotel list.
     *
     * @param model the model
     * @return the hotel list
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/get_hotels")
    public String getHotelList(Model model) {
        model.addAttribute(HOTEL_LIST_ATTRIBUTE, service.getList());
        return HOTEL_LIST_PAGE;
    }
}
