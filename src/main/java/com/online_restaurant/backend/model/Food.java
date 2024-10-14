package com.online_restaurant.backend.model;

import com.online_restaurant.backend.model.Enum.FoodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(nullable = false)
    @NotNull
    private String name;
    @Column(nullable = false)
    @NotNull
    private Double price;
    @Column
    @Length(max = 1000)
    private  String description;
    @Column
    private Boolean status;
    @Enumerated(EnumType.STRING)
    private FoodType type;
    @OneToMany(mappedBy = "food")
    private List<Image> imagePath;
    @OneToMany(mappedBy = "food")
    private List<Comment> comments;
//
//    private List<User> likes;
//    private Integer number;

}
