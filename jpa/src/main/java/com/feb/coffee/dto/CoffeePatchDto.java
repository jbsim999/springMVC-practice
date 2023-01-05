package com.feb.coffee.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CoffeePatchDto {
    @Id
    private long coffeeId;

    @NotBlank
    private String korName;

    @NotBlank
    @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$", message = "커피명(영문)은 영문이어야 합니다. 예) Cafe Latte")
    private String engName;

    @Range(min = 100,max = 50000)
    private int price;
}
