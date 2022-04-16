package com.portfolio.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EducationDto {
    @NotBlank
    private String institution;
    private String title;
    private String link;
    private String date;

}
