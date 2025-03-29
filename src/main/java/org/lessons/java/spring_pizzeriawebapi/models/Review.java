package org.lessons.java.spring_pizzeriawebapi.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Review implements Serializable{
    private static final long serialVersionUID = 1L;  //!Recommended for versioning

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "title cannot be blank.")
    private String title;

    @Column(nullable = false)
    @Size(max = 355, message = "content text cannot overload more than 355chars.")
    @Lob
    private String content = "";  //default value "" !

    @ManyToOne
    @JoinColumn(name="pizza_id", nullable = false)
    @JsonBackReference  //used on the child side
    private Pizza pizza;


    @Override
    public String toString(){
        return String.format("%s %s %s", id, title, content);
        
    }
}
