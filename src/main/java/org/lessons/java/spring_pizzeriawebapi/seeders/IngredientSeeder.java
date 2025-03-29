package org.lessons.java.spring_pizzeriawebapi.seeders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lessons.java.spring_pizzeriawebapi.models.Ingredient;
import org.lessons.java.spring_pizzeriawebapi.models.SpecialOffer;
import org.lessons.java.spring_pizzeriawebapi.repository.IngredientRepository;
import org.lessons.java.spring_pizzeriawebapi.repository.PizzaRepository;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

@Component
public class IngredientSeeder {
    
    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;
    private final Faker faker = new Faker();

    public IngredientSeeder(IngredientRepository ingredientRepository, PizzaRepository pizzaRepository){
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public void seed(){
        if(ingredientRepository.count()==0){
            List<Ingredient> ingredients = new ArrayList<>(Arrays.asList(
                new Ingredient(null, "Tomato", "Fresh tomato sauce", new ArrayList<>()),
                new Ingredient(null, "Mozzarella", "Italian mozzarella cheese", new ArrayList<>()),
                new Ingredient(null, "Basil", "Fresh basil leaves", new ArrayList<>())
            ));
            ingredientRepository.saveAll(ingredients);
        }
    }
}
