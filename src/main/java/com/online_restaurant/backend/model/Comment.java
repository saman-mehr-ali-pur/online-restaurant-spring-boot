package com.online_restaurant.backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,length = 250)
    @Size(max = 250)
    @NotNull
    private String comment;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "foodId",nullable = false)
    private Food food;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId",referencedColumnName = "id",nullable = false)
    private User user;

}
