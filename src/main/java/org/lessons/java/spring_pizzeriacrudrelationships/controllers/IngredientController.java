package org.lessons.java.spring_pizzeriacrudrelationships.controllers;

import java.util.List;
import java.util.Set;

import org.lessons.java.spring_pizzeriacrudrelationships.models.Ingredient;
import org.lessons.java.spring_pizzeriacrudrelationships.models.Pizza;
import org.lessons.java.spring_pizzeriacrudrelationships.repository.IngredientRepository;
import org.lessons.java.spring_pizzeriacrudrelationships.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    
    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;
    @Autowired
    public IngredientController(IngredientRepository ingredientRepository, PizzaRepository pizzaRepository){
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping
    public String ingredientsIndex(Model model){
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        return "ingredients/index.html";
    }

    @GetMapping("/show/{id}")
    public String ingredientsIndex(@PathVariable Integer id,
    Model model){
        Ingredient ingredient = ingredientRepository.findById(id).get();
        model.addAttribute("ingredient", ingredient);
        return "ingredients/show.html";
    }

    @GetMapping("/create")
    public String ingredientsCreate(Model model){
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/create-or-edit.html";
    }

    @PostMapping("/create")
    public String ingredientsStore(@Valid @ModelAttribute("ingredient") Ingredient createIngredient,
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "ingredients/create-or-edit.html";
        }
        ingredientRepository.save(createIngredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String ingredientEdit(@PathVariable Integer id,
    Model model){
        model.addAttribute("ingredient", ingredientRepository.findById(id).get());
        model.addAttribute("edit", true);  //used in the view with th:if x view edit or create
        return "ingredients/create-or-edit.html";
    }

    @PostMapping("/edit/{id}")
    public String ingredientsEditStore(@Valid @ModelAttribute("ingredient") Ingredient editIngredient,
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){ 
            model.addAttribute("edit", true);
            return "ingredients/create-or-edit.html"; 
        }
        ingredientRepository.save(editIngredient);
        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String ingredientsDelete(@PathVariable Integer id){
        Ingredient ingredientTotDelete = ingredientRepository.findById(id).get();
        for(Pizza linkedPizza : ingredientTotDelete.getPizzas()){
            linkedPizza.getIngredients().remove(ingredientTotDelete);
        }
        ingredientRepository.delete(ingredientTotDelete);
        return "redirect:/ingredients";
    }

}
