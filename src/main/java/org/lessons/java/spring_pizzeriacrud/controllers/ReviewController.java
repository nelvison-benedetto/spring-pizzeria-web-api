package org.lessons.java.spring_pizzeriacrud.controllers;

import java.util.List;

import org.lessons.java.spring_pizzeriacrud.models.Review;
import org.lessons.java.spring_pizzeriacrud.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    
    private final ReviewRepository reviewRepository;
    @Autowired
    public ReviewController(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public String reviewsIndex(Model model){
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews != null ? reviews : List.of());  //ensures that reviews is never null!!
        return "";   //to show all reviews in /reviews page
    }

    @GetMapping("/create")
    public String reviewsCreate(Model model){
        model.addAttribute("review", new Review());
        return "";
    }


}
