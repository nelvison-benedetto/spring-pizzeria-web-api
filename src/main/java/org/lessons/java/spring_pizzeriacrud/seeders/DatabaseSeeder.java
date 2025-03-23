package org.lessons.java.spring_pizzeriacrud.seeders;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner{
    
    private final PizzaSeeder pizzaSeeder;
    private final ReviewSeeder reviewSeeder;

    @Autowired  //inject instance PizzaSeeder & ReviewSeeder in this instance DatabaseSeeder
    public DatabaseSeeder(PizzaSeeder pizzaSeeder, ReviewSeeder reviewSeeder){
        this.pizzaSeeder = pizzaSeeder;
        this.reviewSeeder = reviewSeeder;
    }

    @Override
    public void run(String... args){
        pizzaSeeder.seed();
        reviewSeeder.seed(12);
    }
}
