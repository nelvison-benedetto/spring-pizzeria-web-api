package org.lessons.java.spring_pizzeriacrud.controllers;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lessons.java.spring_pizzeriacrud.models.Pizza;
import org.lessons.java.spring_pizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.datafaker.providers.base.Book;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    
    private final PizzaRepository repository;

    @Autowired  //inject instance of PizzaRepository in this instance of PizzaController, from spring v 4.3+ here not necessary explicit(@...) if only 1 constr
    public PizzaController(PizzaRepository repository){
        this.repository = repository;
    }


    @GetMapping  //without path is a get to '/pizzas'
    public String pizzasIndex(Model model){
        List<Pizza> pizzas = repository.findAll();
        model.addAttribute("pizzas", pizzas != null ? pizzas : List.of());   //ensures that pizzas is never null!!
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String pizzasShow(@PathVariable("id") Integer id,
    Model model){
        Pizza pizza = repository.findById(id).get();
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    //filters
    @GetMapping("/searchByForm")  
    public String searchByForm(
    @RequestParam(name="title", required = false, defaultValue = "") String title,
    @RequestParam(name="content", required = false, defaultValue = "") String content,
    @RequestParam(name = "restrictions", required = false) Set<String> restrictions,
    Model model){
        if(restrictions==null){
            System.out.println("restrictions is ex-NULL!");
            restrictions = new HashSet<>();
        }
        
        List<Pizza> pizzas;      
        if (restrictions.isEmpty()) {  
            if (title == null || title.isBlank()){
                pizzas = repository.findByContentContaining(content); 
            }
            else if (content == null || content.isBlank()) {  
                pizzas = repository.findByTitleContaining(title);  
            } 
            else {  
                pizzas = repository.findByTitleContainingAndContentContaining(title, content);  
            }  
        }else {  
            if (title == null || title.isBlank()) {  
                pizzas = repository.findByContentContainingAndRestrictionsIn(content, restrictions);  
            }else if(content == null || content.isBlank()){  
                pizzas = repository.findByTitleContainingAndRestrictionsIn(title, restrictions);
            } else{
                pizzas = repository.findByTitleContainingAndContentContainingAndRestrictionsIn(title, content, restrictions); 
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
        List<Pizza> pizzas = repository.findByPriceBetween(min, max);
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
