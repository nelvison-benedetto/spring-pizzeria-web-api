package org.lessons.java.spring_pizzeriawebapi.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ingredients")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Ingredient implements Serializable{ //pizza is a JavaBean and a JPA entity
    private static final long serialVersionUID = 1L;  //!Recommended for versioning

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "name cannot be blank.")
    private String title;

    @Column(nullable = false)
    @Size(max = 355, message = "content text cannot overload more than 355chars.")
    //@Lob //x text very very long(i.e. un articolo completo di giornale)! altrimenti lascia string(varchar)
    private String content = "";  //default value "" !

    @ManyToMany(mappedBy = "ingredients")
    @JsonBackReference  //used on the child side(x many-to-many no add @JsonManagedReference to the parent)
    private List<Pizza> pizzas = new ArrayList<>();  //x many-to-many relations it's better SET<> because we don't want duplicates

    @Override
    public String toString(){
        return String.format("%s %s %s", id, title, content);
    }
}
