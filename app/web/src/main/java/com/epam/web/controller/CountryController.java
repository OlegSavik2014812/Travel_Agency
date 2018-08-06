package com.epam.web.controller;

import com.epam.core.entity.Country;
import com.epam.core.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Country controller.
 */
@Controller
public class CountryController {
    private static final String COUNTRY_LIST_PAGE = "country_list";
    private static final String COUNTRY_LIST_ATTRIBUTE = "countryList";
    private final CountryService service;

    /**
     * Instantiates a new Country controller.
     *
     * @param service the service
     */
    @Autowired
    public CountryController(CountryService service) {
        this.service = service;
    }

    /**
     * Gets country list.
     *
     * @param model the model
     * @return the country list
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/get_countries")
    public String getCountryList(Model model) {
        List<Country> list = service.getList();
        model.addAttribute(COUNTRY_LIST_ATTRIBUTE, list);
        return COUNTRY_LIST_PAGE;
    }
}
