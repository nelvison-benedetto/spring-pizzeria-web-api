package org.lessons.java.spring_pizzeriacrud.seeders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.lessons.java.spring_pizzeriacrud.models.Pizza;
import org.lessons.java.spring_pizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

@Component
public class PizzaSeeder {
    
    private final PizzaRepository pizzaRepository;
    private final Faker faker = new Faker();

    @Autowired  //inject instance of PizzaRepository in this instance of PizzaSeeder, from spring v 4.3+ here not necessary explicit(@...) if only 1 constr
    public PizzaSeeder(PizzaRepository pizzaRepository){
        this.pizzaRepository = pizzaRepository;
    }

    public void seed(int num){
        List<Pizza> pizzas = new ArrayList<>();
        for(int i=0; i<num; i++){
            pizzas.add(new Pizza(
                null,
                faker.food().dish(),
                faker.lorem().sentence(),
                faker.internet().image(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 5, 30))
            ));
        }
        pizzaRepository.saveAll(pizzas);
    }
}
