package org.lessons.java.spring_pizzeriawebapi.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.lessons.java.spring_pizzeriawebapi.models.Pizza;
import org.lessons.java.spring_pizzeriawebapi.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
    
    private final PizzaRepository pizzaRepository;
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository){
        this.pizzaRepository = pizzaRepository;
    }


    public List<Pizza> findAll(){
        return pizzaRepository.findAll();
    }
    public List<Pizza> findAllSortedByTitle(){
        return pizzaRepository.findAll(Sort.by("title"));
    }

    public Pizza getById(Integer id){
        //Option<Pizza> pizzaAttempt = pizzaRepository.findById(id);
        return pizzaRepository.findById(id).orElseThrow(()->new RuntimeException("Pizza not found."));
    }
    public Optional<Pizza> findById(Integer id){
        return pizzaRepository.findById(id);
    }


    public Pizza create(Pizza pizzaCreate){
        return pizzaRepository.save(pizzaCreate);
    }
    public Pizza edit(Pizza pizzaEdit){
        //logic update of target fields after the operation
        return pizzaRepository.save(pizzaEdit);
    }
    public void delete(Pizza pizzaDelete){
        pizzaRepository.delete(pizzaDelete);
    }
    public void deleteById(Integer id){
        Pizza pizzaToDelete = getById(id);
        pizzaRepository.delete(pizzaToDelete);
    }
    public Boolean existsById(Integer id){
        return pizzaRepository.existsById(id);
    }
    public Boolean exists(Pizza pizza){
        return existsById(pizza.getId());
    }


    public List<Pizza> findByTitleContaining(String title){
        return pizzaRepository.findByTitleContaining(title);
    }
    public List<Pizza> findByContentContaining(String content){
        return pizzaRepository.findByContentContaining(content);
    }
    public List<Pizza> findByTitleContainingAndContentContaining(String title, String content){
        return pizzaRepository.findByTitleContainingAndContentContaining(title, content);
    }

    public List<Pizza> findByTitleContainingAndRestrictionsIn(String title, Set<String> restrictions){
        return pizzaRepository.findByTitleContainingAndRestrictionsIn(title, restrictions);
    }
    public List<Pizza> findByContentContainingAndRestrictionsIn(String content, Set<String> restrictions){
        return pizzaRepository.findByContentContainingAndRestrictionsIn(content, restrictions);
    }
    public List<Pizza> findByTitleContainingAndContentContainingAndRestrictionsIn(String title, String content, Set<String> restrictions){
        return pizzaRepository.findByTitleContainingAndContentContainingAndRestrictionsIn(title, content, restrictions);
    }

}
    