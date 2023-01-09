package com.feb.coffee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
    @Id
    private long coffeeId;
    private String korName;
    private String engName;
    private int price;
    private String coffeeCode;
}
