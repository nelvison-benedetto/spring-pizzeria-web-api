package org.lessons.java.spring_pizzeriacrudrelationships.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pizzas")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Pizza implements Serializable{
    private static final long serialVersionUID = 1L;  //!Recommended for versioning

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "title connot be blank.")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "content cannot be blank.")
    @Size(max=355, message = "content text cannot overload more than 355chars.")  //if u use seeder, set this to many many chars otherwise will get convalid errors
    @Lob
    private String content;

    @Column(nullable = false)
    @NotBlank(message = "picture str cannot be blank.")
    private String picture;

    @Column(nullable = false)
    @NotNull(message = "price cannot be null.")
    @Min(value = 0, message = "price cannot be negative.")
    private BigDecimal price;

    @ElementCollection(fetch = FetchType.EAGER)  // Stores as a separate table
    @CollectionTable(name = "pizza_restrictions", joinColumns = @JoinColumn(name = "pizza_id"))
    @Column(name = "restriction")
    private Set<String> restrictions = new HashSet<>();  //IT WILL NEVER BE NULL!verygood 

    // @NotNull
    // private LocalDate publicationDate;

    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true)  //"pizza" al singolare!, CascadeType.All better delete also updates-persists-merges,orphanRemoval ensures if a review is removed from reviews it will be also deleted from the db
    @JsonManagedReference  //used on the parent side
    private List<Review> reviews = new ArrayList<>();  //set as empty! so it won't be null!!


    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  //used on the parent side
    private List<SpecialOffer> specialoffers = new ArrayList<>();  //set as empty! so it won't be null!!

    @ManyToMany
    @JoinTable(
        name = "ingredient_pizza",  //singolar, alphabetic order
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();  //x many-to-many relations better SET<> because we don't want duplicates


    
    @Override
    public String toString(){
        return String.format("%s %s %s", id, title, price);
    }
}
