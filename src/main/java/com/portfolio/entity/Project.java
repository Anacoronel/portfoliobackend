package com.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.security.entity.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String projectImg;
    private String description;
    private String startTime;
    private String endTime;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Project(String nombre, String projectImg, String description, String startTime, String endTime) {
        this.name = nombre;
        this.projectImg = projectImg;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
