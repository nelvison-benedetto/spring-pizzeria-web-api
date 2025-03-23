package org.lessons.java.spring_pizzeriacrud.controllers;

import java.util.List;

import org.lessons.java.spring_pizzeriacrud.models.Review;
import org.lessons.java.spring_pizzeriacrud.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ReviewRepository reviewRepository;
    @Autowired
    public HomeController(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @GetMapping  //without path is a get to '/'
    public String homeIndex(Model model){
        model.addAttribute("page", "home");  //! x header to decide if use position-absolute or not
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        model.addAttribute("review", new Review());  //!add empty review x the POST form! necessary to use 'th:object="${review}"'
        return "index";
    }

    @GetMapping("/create")
    public String reviewsCreate(Model model){
        model.addAttribute("review", new Review());
        return "";
    }
    @PostMapping("/")
    public String reviewsStore(@Valid @ModelAttribute("review") Review formReview,
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){ 
            model.addAttribute("reviews", reviewRepository.findAll());  //load all existing reviews
            model.addAttribute("page", "home");
            return "index"; 
        }
        reviewRepository.save(formReview);
        return "redirect:/";  //!redirect prevents a second POST if u refresh the page after submitting the form
    }
}
