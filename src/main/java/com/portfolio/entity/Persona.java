package com.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.security.entity.User;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Persona {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String city;
    private String country;

    private String backImg;
    private String profileImg;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public Persona(String name,String description, String city, String country, String backImg, String profileImg) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.country = country;
        this.backImg = backImg;
        this.profileImg = profileImg;
    }
}
