package org.lessons.java.spring_pizzeriacrud.controllers;

import java.util.List;

import org.lessons.java.spring_pizzeriacrud.models.Review;
import org.lessons.java.spring_pizzeriacrud.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


    // @GetMapping("/create")  //useful only to redirect user to create web page
    // public String reviewsCreate(Model model){
    //     model.addAttribute("review", new Review());
    //     return "";
    // }
    @PostMapping("/create")
    public String reviewsStore(@Valid @ModelAttribute("review") Review storedReview,  //("review") of @ModelAttribute useful for th:object in the html, however always explicit x consistency
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){ 
            model.addAttribute("reviews", reviewRepository.findAll());  //load all existing reviews
            model.addAttribute("page", "home");
            return "index"; 
        }
        reviewRepository.save(storedReview);
        return "redirect:/";  //!redirect prevents a second POST if u refresh the page after submitting the form
    }

    @PutMapping("/reviews/update/{id}")
    public String reviewsUpdate(@PathVariable Integer id, @Valid @ModelAttribute("review") Review updatedReview,  //("review") of @ModelAttribute useful for th:object in the html, however always explicit x consistency
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("reviews", reviewRepository.findAll());  //load all existing reviews
            model.addAttribute("page", "home");
            return "index";
        }
        if(reviewRepository.existsById(id)){
            updatedReview.setId(id);  //set same old id 
            reviewRepository.save(updatedReview);
        }
        return "redirect:/";
    }

    // @PostMapping("/reviews/delete/{id}")
    // public String reviewsDelete(@PathVariable Integer id){
    //     if (reviewRepository.existsById(id)){  //check if id exist!!
    //         reviewRepository.deleteById(id);
    //     }
    //     return "redirect:/";
    // }

    @DeleteMapping("/reviews/delete/{id}")   //!IT'S BETTER to use @DeleteMapping
    public String reviewsDelete(@PathVariable Integer id){
        if(reviewRepository.existsById(id)){
            reviewRepository.deleteById(id);
        }
        return "redirect:/";
    }
}
