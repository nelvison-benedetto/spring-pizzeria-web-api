package org.lessons.java.spring_pizzeriawebapi.controllers;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lessons.java.spring_pizzeriawebapi.models.Ingredient;
import org.lessons.java.spring_pizzeriawebapi.models.Pizza;
import org.lessons.java.spring_pizzeriawebapi.models.Review;
import org.lessons.java.spring_pizzeriawebapi.models.SpecialOffer;
import org.lessons.java.spring_pizzeriawebapi.repository.IngredientRepository;
import org.lessons.java.spring_pizzeriawebapi.repository.PizzaRepository;
import org.lessons.java.spring_pizzeriawebapi.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import net.datafaker.providers.base.Book;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    
    private final PizzaRepository pizzaRepository;
    private final SpecialOfferRepository specialOfferRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired  //inject instance of PizzaRepository in this instance of PizzaController, from spring v 4.3+ here not necessary explicit(@...) if only 1 constr
    public PizzaController(PizzaRepository pizzaRepository, SpecialOfferRepository specialOfferRepository, IngredientRepository ingredientRepository){
        this.pizzaRepository = pizzaRepository;
        this.specialOfferRepository = specialOfferRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping  //without path is a get to '/pizzas'
    public String pizzasIndex(Model model){
        List<Pizza> pizzas = pizzaRepository.findAll();  //.find() can return empty but never null!
        model.addAttribute("pizzas", pizzas);  
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String pizzasShow(@PathVariable("id") Integer id,
    Model model){
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pizza not found."));
        Review review = new Review();
        review.setPizza(pizza);  //link the pizza to the new review

        SpecialOffer specialoffer = new SpecialOffer();
        specialoffer.setPizza(pizza);  //link the pizza to the new special offer
        
        model.addAttribute("pizza", pizza);
        model.addAttribute("review", review);
        model.addAttribute("specialoffer", specialoffer);
        // System.out.println("reviews linked to this pizza: "+pizza.getReviews());
        // System.out.println("specialoffers linked to this pizza: "+pizza.getSpecialoffers());
        return "pizzas/show";
    }

    @GetMapping("/create")
    public String createPizza(Model model){
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/create-or-edit.html";
    }
    @PostMapping("/create")
    public String storePizza(@Valid @ModelAttribute("pizza") Pizza createPizza,
    BindingResult bindingResult, Model model){
        System.out.println("POST RECEIVED!!");
        if(bindingResult.hasErrors()){ 
            System.out.println("returning x binding!!!");
            model.addAttribute("ingredients", ingredientRepository.findAll()); //otherwise at the return there will be no ingredients
            return "pizzas/create-or-edit.html"; 
        }
        pizzaRepository.save(createPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("edit/{id}")
    public String editPizza(@PathVariable Integer id, Model model){
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        model.addAttribute("edit", true);
        return "pizzas/create-or-edit.html";
    }

    @PostMapping("edit/{id}")
    public String editPizza(@Valid @ModelAttribute("pizza") Pizza editPizza,
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "pizzas/create-or-edit.html";
        }
        pizzaRepository.save(editPizza);
        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Integer id){
        if(pizzaRepository.existsById(id)){
            pizzaRepository.deleteById(id);
        }
        return "redirect:/pizzas";
    }

    //filters
    @GetMapping("/searchByForm")  
    public String searchByForm(
    @RequestParam(name="title", required = false, defaultValue = "") String title,
    @RequestParam(name="content", required = false, defaultValue = "") String content,
    @RequestParam(name = "restrictions", required = false) Set<String> restrictions,
    Model model){
        if(restrictions==null){
            restrictions = new HashSet<>();
            System.out.println("restrictions is ex-NULL!");
        }
        List<Pizza> pizzas;      
        if (restrictions.isEmpty()) {  
            if (title == null || title.isBlank()){
                pizzas = pizzaRepository.findByContentContaining(content); 
            }
            else if (content == null || content.isBlank()) {  
                pizzas = pizzaRepository.findByTitleContaining(title);  
            } 
            else {  
                pizzas = pizzaRepository.findByTitleContainingAndContentContaining(title, content);  
            }  
        }else {  
            if (title == null || title.isBlank()) {  
                pizzas = pizzaRepository.findByContentContainingAndRestrictionsIn(content, restrictions);  
            }else if(content == null || content.isBlank()){  
                pizzas = pizzaRepository.findByTitleContainingAndRestrictionsIn(title, restrictions);
            } else{
                pizzas = pizzaRepository.findByTitleContainingAndContentContainingAndRestrictionsIn(title, content, restrictions); 
            }
        }
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }


    @GetMapping("/searchByPriceBetween")
    public String searchByPriceBetween(
    @RequestParam(name="min", defaultValue ="0") BigDecimal min,
    @RequestParam(name="max", defaultValue = "100") BigDecimal max,
    Model model){
        List<Pizza> pizzas = pizzaRepository.findByPriceBetween(min, max);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    // @GetMapping("/searchByTitleOrAuthor")  //http://localhost:8080/books/searchByTitleOrAuthor?query=alejandra
    // public String searchByTitleOrAuthor(@RequestParam(name="query") String query,
    // Model model){
    //     List<Book> books= repository.findByTitleContainingOrAuthorContaining(query, query);  //search same str in title and also in author columns
    //     model.addAttribute("books", books);
    //     return "books/index";
    // }


}
