package org.lessons.java.spring_pizzeriawebapi.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_pizzeriawebapi.models.Pizza;
import org.lessons.java.spring_pizzeriawebapi.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaRestController {
    
    private final PizzaService pizzaService;
    @Autowired
    public PizzaRestController(PizzaService pizzaService){
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public List<Pizza> pizzasIndex(){
        List<Pizza> pizzas = pizzaService.findAll();
        return pizzas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@PathVariable Integer id){
        Optional<Pizza> pizzaOptional = pizzaService.findById(id);
        if(pizzaOptional.isPresent()){   //isPresent check if exists, isEmpty check if exist but is slightly harder to read
            return new ResponseEntity<Pizza>(pizzaOptional.get(), HttpStatus.OK);      
        }
        return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizzaToStore){  //i am not received a form, but a http w bodyrequest
        return new ResponseEntity<Pizza>(pizzaService.create(pizzaToStore), HttpStatus.CREATED);  //201 created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> update(@Valid @RequestBody Pizza pizzaToUpdate, @PathVariable Integer id){
        if(pizzaService.findById(id).isPresent()){
            pizzaToUpdate.setId(id);
            return new ResponseEntity<Pizza>(pizzaService.edit(pizzaToUpdate), HttpStatus.OK);   
        }
        return new ResponseEntity<Pizza>(pizzaService.edit(pizzaToUpdate), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pizza> delete(@Valid @PathVariable Integer id){
        if(pizzaService.findById(id).isPresent()){
            pizzaService.deleteById(id);
            return new ResponseEntity<Pizza>(HttpStatus.NO_CONTENT);  
        }
        return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
    }
}
