package com.online_restaurant.backend.model;

import com.online_restaurant.backend.model.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(nullable = false,unique = true)
    @NaturalId
    private String username;
    @Column
    private String password;
    @Column(nullable = false,unique = true)
    @NaturalId
    private String email;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date signupDate;
    @Column(name = "ROLE",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user",fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE})
    private Address address;

}
