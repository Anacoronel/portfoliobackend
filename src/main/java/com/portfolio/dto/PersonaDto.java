package com.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDto {
    private String name;
    private String description;
    private String city;
    private String country;
    private String backImg;
    private String profileImg;
}
