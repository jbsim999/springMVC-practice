package com.codestates.coffee;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class CoffeePostDto {
    @NotBlank
    private String korName;
    @NotBlank
    private String engName;
    @Min(10)
    @Max(50000)
    private int price;
}
