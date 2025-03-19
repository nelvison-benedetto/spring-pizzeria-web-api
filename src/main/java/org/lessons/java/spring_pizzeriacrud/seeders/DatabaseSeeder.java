package org.lessons.java.spring_pizzeriacrud.seeders;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner{
    
    private final PizzaSeeder pizzaSeeder;

    @Autowired  //inject instance PizzaSeeder in this instance DatabaseSeeder
    public DatabaseSeeder(PizzaSeeder pizzaSeeder){
        this.pizzaSeeder = pizzaSeeder;
    }

    @Override
    public void run(String... args){
        pizzaSeeder.seed(10);
    }
}
