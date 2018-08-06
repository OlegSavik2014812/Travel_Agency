package com.epam.web.controller;

import com.epam.core.entity.Tour;
import com.epam.core.service.TourService;
import com.epam.core.util.JsonTourParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type File upload controller.
 */
@PreAuthorize("hasAuthority('ADMIN')")
@Controller
public class FileUploadController {
    private static final String UPLOADED_LIST_PAGE = "uploaded_list";
    private static final String TOUR_LIST_ATTRIBUTE = "tourList";
    private final TourService service;

    /**
     * Instantiates a new File upload controller.
     *
     * @param service the service
     */
    @Autowired
    public FileUploadController(TourService service) {
        this.service = service;
    }


    /**
     * Upload file string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value = "/upload")
    public String uploadFile(Model model) {
        model.addAttribute(TOUR_LIST_ATTRIBUTE, new ArrayList<>());
        return UPLOADED_LIST_PAGE;
    }

    /**
     * Read tours string.
     *
     * @param multipartFile the multipart file
     * @param model         the model
     * @return the string
     * @throws IOException the io exception
     */
    @PostMapping(value = "/upload_tour_list")
    public String readTours(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        File file = getFile(multipartFile);
        Optional<List<Tour>> optionalTours = Optional.ofNullable(JsonTourParser.parse(file));
        List<Tour> tourList = optionalTours.orElse(new ArrayList<>());
        for (Tour tour : tourList) {
            service.add(tour);
        }
        model.addAttribute(TOUR_LIST_ATTRIBUTE, tourList);
        return UPLOADED_LIST_PAGE;
    }

    private File getFile(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(multipartFile.getOriginalFilename());
        Files.write(path, bytes);
        return new File(String.valueOf(path));
    }
}
