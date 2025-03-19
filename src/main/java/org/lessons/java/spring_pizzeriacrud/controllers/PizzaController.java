package org.lessons.java.spring_pizzeriacrud.controllers;

import org.lessons.java.spring_pizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    
    private final PizzaRepository repository;

    @Autowired  //inject instance of PizzaRepository in this instance of PizzaController, from spring v 4.3+ here not necessary explicit(@...) if only 1 constr
    public PizzaController(PizzaRepository repository){
        this.repository = repository;
    }

}
