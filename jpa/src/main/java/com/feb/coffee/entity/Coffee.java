package com.feb.coffee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
//@AllArgsConstructor
public class Coffee {
    @Id
    private long coffeeId;
    private String korName;
    private String engName;
    private int price;
    private String coffeeCode;

    public Coffee(long coffeeId, String korName, String engName, int price) {
        this.coffeeId = coffeeId;
        this.korName = korName;
        this.engName = engName;
        this.price = price;
    }
}
