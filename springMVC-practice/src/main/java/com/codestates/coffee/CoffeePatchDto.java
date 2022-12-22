package com.codestates.coffee;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CoffeePatchDto {
    private long coffeeId;
    @NotBlank
    private String korName;
    @Min(10)
    @Max(50000)
    private int price;
}
