package org.lessons.java.spring_pizzeriacrud.controllers;
import java.math.BigDecimal;
import java.util.List;
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
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String pizzasShow(@PathVariable("id") Integer id,
    Model model){
        Pizza pizza = repository.findById(id).get();
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    @GetMapping("/searchByName")  //http://localhost:8080/pizzas/searchByName?name=pho
    public String searchByName(@RequestParam(name="name") String name,
    Model model){
        List<Pizza> pizzas = repository.findByNameContaining(name);
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
