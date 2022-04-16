package com.portfolio.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExperienceDto {
    @NotBlank
    private String position;
    @NotBlank
    private String company;
    private String link;
    @NotBlank
    private String startTime;
    private String endTime;
}
