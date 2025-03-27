package org.lessons.java.spring_pizzeriacrudrelationships.controllers;

import java.util.List;

import org.lessons.java.spring_pizzeriacrudrelationships.models.Pizza;
import org.lessons.java.spring_pizzeriacrudrelationships.models.Review;
import org.lessons.java.spring_pizzeriacrudrelationships.repository.PizzaRepository;
import org.lessons.java.spring_pizzeriacrudrelationships.repository.ReviewRepository;
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
@RequestMapping("/reviews")
public class ReviewController {
    
    private final ReviewRepository reviewRepository;
    private final PizzaRepository pizzaRepository;
    @Autowired
    public ReviewController(ReviewRepository reviewRepository, PizzaRepository pizzaRepository){
        this.reviewRepository = reviewRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping
    public String reviewsIndex(Model model){
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews != null ? reviews : List.of());  //ensures that reviews is never null!!
        return "";   //to show all reviews in /reviews page
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

    @PutMapping("/update/{id}")
    public String reviewsUpdate(@PathVariable Integer id, @Valid @ModelAttribute("review") Review updatedReview,  //("review") of @ModelAttribute useful for th:object in the html, however always explicit x consistency
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            // model.addAttribute("reviews", reviewRepository.findAll());  //load all existing reviews
            // model.addAttribute("page", "home");
            return "redirect:/pizzas/"+updatedReview.getPizza().getId();
        }
        if(reviewRepository.existsById(id)){
            updatedReview.setId(id);  //ensures set same old id!
            reviewRepository.save(updatedReview);
        }
        System.out.println("pizza linked to updated review: "+updatedReview.getPizza().getTitle());
        return "redirect:/pizzas/"+updatedReview.getPizza().getId();
    }

    @DeleteMapping("/delete/{id}")   //!IT'S BETTER to use @DeleteMapping
    public String reviewsDelete(@PathVariable Integer id){
        if(reviewRepository.existsById(id)){
            reviewRepository.deleteById(id);
        }
        return "redirect:/pizzas";
    }


    //Operation on target Pizza's reviews
    
    @GetMapping("/{id}/form")  //to redirect to target page for ur pizza. i don't use it anyway but to know
    public String reviewsIdPizzaFormCreateReview(@PathVariable Integer id, Model model){
        Pizza pizza = pizzaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pizza not found."));
        Review review =  new Review();
        review.setPizza(pizza);  //do .get() if find id pizza, otherwise thrown error
            //set field 'pizza' of instance Review, because each review is linked to 1 pizza.
        System.out.println("pizza Linked to this review obj "+review.getPizza());
        System.out.println("this review id(generated) "+review.getId());
        model.addAttribute("review", review);
        return "reviews/create";
    }

    @PostMapping("/{id}/create")  //this only function is to store the new review on db, no models/others passed to exit 
    public String reviewsCreatePizzaId(@Valid @ModelAttribute("review") Review formReview,  //takes the Review object!!However the form that sends the Review object must first have received a Review object with only the pizza_id field set(using review.setPizza(pizza)
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "reviews/create";
            // return "redirect:/pizzas/"+formReview.getPizza().getId();
            //return "pizzas/show";  //to test, but u need override w @PostMapping("/pizzas/{id}/create")
        }
        formReview.setId(null);  //ok!! before the review ID was always set to the same value as its linked pizza ID

        System.out.println("review id "+formReview.getId());
        System.out.println("pizza linked "+formReview.getPizza());

        reviewRepository.save(formReview);
        System.out.println("saved successfully the new review w ID: "+formReview.getId());
        return "redirect:/pizzas/"+formReview.getPizza().getId();
    }

}
