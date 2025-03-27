package org.lessons.java.spring_pizzeriacrudrelationships.controllers;

import org.lessons.java.spring_pizzeriacrudrelationships.repository.PizzaRepository;
import org.lessons.java.spring_pizzeriacrudrelationships.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SpecialOfferController {
    
    private final SpecialOfferRepository specialOfferRepository;
    @Autowired  //inject instance of SpecialOfferRepository in this instance of PizzaController, from spring v 4.3+ here not necessary explicit(@...) if only 1 constr
    public SpecialOfferController(SpecialOfferRepository specialOfferRepository){
        this.specialOfferRepository = specialOfferRepository;
    }

    
}
