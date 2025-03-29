package org.lessons.java.spring_pizzeriawebapi.seeders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lessons.java.spring_pizzeriawebapi.models.Pizza;
import org.lessons.java.spring_pizzeriawebapi.models.SpecialOffer;
import org.lessons.java.spring_pizzeriawebapi.repository.PizzaRepository;
import org.lessons.java.spring_pizzeriawebapi.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

@Component
public class SpecialOfferSeeder {
    
    private final SpecialOfferRepository specialOfferRepository;
    private final PizzaRepository pizzaRepository;
    private final Faker faker = new Faker();

    @Autowired
    public SpecialOfferSeeder(SpecialOfferRepository specialOfferRepository, PizzaRepository pizzaRepository){
        this.specialOfferRepository = specialOfferRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public void seed(){
        if(specialOfferRepository.count()==0){
            Pizza pizza = pizzaRepository.findById(1).get();
            List<SpecialOffer> specialoffers = new ArrayList<>(Arrays.asList(
                
                new SpecialOffer(null, "Summer Pizza Festival", LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 30), pizza),
                new SpecialOffer(null, "Family Combo Weekend", LocalDate.of(2025, 4, 12), LocalDate.of(2025, 4, 14), pizza),
                new SpecialOffer(null, "Spring Toppings Madness", LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 15), pizza),
                new SpecialOffer(null, "2x1 Margherita Days", LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 10), pizza),
                new SpecialOffer(null, "Pizza & Drink Combo", LocalDate.of(2025, 3, 28), LocalDate.of(2025, 4, 5), pizza),
                new SpecialOffer(null, "Student Discount Weeks", LocalDate.of(2025, 4, 15), LocalDate.of(2025, 5, 15), pizza),
                new SpecialOffer(null, "Birthday Party Special", LocalDate.of(2025, 4, 20), LocalDate.of(2025, 4, 22), pizza),
                new SpecialOffer(null, "Cheese Lovers Promo", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 20), pizza),
                new SpecialOffer(null, "Family Pizza Night", LocalDate.of(2025, 3, 30), LocalDate.of(2025, 4, 30), pizza),
                new SpecialOffer(null, "Golden Crust Special", LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 31), pizza)
            ));
            specialOfferRepository.saveAll(specialoffers);
        }
    }
}   
