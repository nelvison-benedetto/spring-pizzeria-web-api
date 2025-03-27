package org.lessons.java.spring_pizzeriacrudrelationships.controllers;

import org.lessons.java.spring_pizzeriacrudrelationships.models.Pizza;
import org.lessons.java.spring_pizzeriacrudrelationships.models.Review;
import org.lessons.java.spring_pizzeriacrudrelationships.models.SpecialOffer;
import org.lessons.java.spring_pizzeriacrudrelationships.repository.PizzaRepository;
import org.lessons.java.spring_pizzeriacrudrelationships.repository.SpecialOfferRepository;
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
@RequestMapping("/specialoffers")
public class SpecialOfferController {
    
    private final SpecialOfferRepository specialOfferRepository;
    private final PizzaRepository pizzaRepository;
    @Autowired  //inject instance of SpecialOfferRepository in this instance of PizzaController, from spring v 4.3+ here not necessary explicit(@...) if only 1 constr
    public SpecialOfferController(SpecialOfferRepository specialOfferRepository, PizzaRepository pizzaRepository){
        this.specialOfferRepository = specialOfferRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/{id}/form")  //to redirect to target page for ur pizza. i don't use it anyway but to know
    public String specialoffersIdPizzaFormCreateSpecialOffer(@PathVariable Integer id, Model model){
        System.out.println("id pizza received: "+id);
        Pizza pizza = pizzaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pizza not found."));
        SpecialOffer specialoffer =  new SpecialOffer();
        specialoffer.setPizza(pizza);  //do .get() if find id pizza, otherwise thrown error
            //set field 'pizza' of instance Review, because each review is linked to 1 pizza.
        System.out.println("pizza Linked to this specialoffer obj "+specialoffer.getPizza());
        System.out.println("this specialoffer id(generated) "+specialoffer.getId());
        model.addAttribute("specialoffer", specialoffer);
        return "specialoffers/create";
    }

    @PostMapping("/{id}/create")  //this only function is to store the new review on db, no models/others passed to exit 
    public String specialsCreatePizzaId(@Valid @ModelAttribute("specialoffer") SpecialOffer formRSpecialOffer,  //takes the Review object!!However the form that sends the Review object must first have received a Review object with only the pizza_id field set(using review.setPizza(pizza)
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "specialoffers/create";
            // return "redirect:/pizzas/"+formReview.getPizza().getId();
            //return "pizzas/show";  //to test, but u need override w @PostMapping("/pizzas/{id}/create")
        }
        formRSpecialOffer.setId(null);  //ok!! before the review ID was always set to the same value as its linked pizza ID

        System.out.println("pizza linked "+formRSpecialOffer.getPizza());

        specialOfferRepository.save(formRSpecialOffer);
        System.out.println("saved successfully the new specialoffer w ID: "+formRSpecialOffer.getId());
        return "redirect:/pizzas/"+formRSpecialOffer.getPizza().getId();
    } 

    @PutMapping("/update/{id}")  //id's of specialoffer
    public String specialsUpdate(@PathVariable Integer id, @Valid @ModelAttribute("specialoffer") SpecialOffer updatedSpecialOffer,  //("review") of @ModelAttribute useful for th:object in the html, however always explicit x consistency
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            // model.addAttribute("reviews", reviewRepository.findAll());  //load all existing reviews
            // model.addAttribute("page", "home");
            return "redirect:/pizzas/"+updatedSpecialOffer.getPizza().getId();
        }
        if(specialOfferRepository.existsById(id)){
            updatedSpecialOffer.setId(id);  //ensures set same old id!
            specialOfferRepository.save(updatedSpecialOffer);
        }
        System.out.println("pizza linked to updated review: "+updatedSpecialOffer.getPizza().getTitle());
        return "redirect:/pizzas/"+updatedSpecialOffer.getPizza().getId();
    }

    @DeleteMapping("/delete/{id}")   //!IT'S BETTER to use @DeleteMapping
    public String specialsDelete(@PathVariable Integer id){
        if(specialOfferRepository.existsById(id)){
            specialOfferRepository.deleteById(id);
        }
        return "redirect:/pizzas";
    }


}
