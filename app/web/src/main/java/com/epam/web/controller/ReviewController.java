package com.epam.web.controller;

import com.epam.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Review controller.
 */
@Controller
public class ReviewController {
    private static final String REVIEW_LIST_PAGE = "review_list";
    private static final String REVIEW_LIST_ATTRIBUTE = "reviewList";
    private final ReviewService service;

    /**
     * Instantiates a new Review controller.
     *
     * @param service the service
     */
    @Autowired
    public ReviewController(ReviewService service) {
        this.service = service;
    }

    /**
     * Gets review list.
     *
     * @param model the model
     * @return the review list
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/get_reviews")
    public String getReviewList(Model model) {
        model.addAttribute(REVIEW_LIST_ATTRIBUTE, service.getList());
        return REVIEW_LIST_PAGE;
    }

}
