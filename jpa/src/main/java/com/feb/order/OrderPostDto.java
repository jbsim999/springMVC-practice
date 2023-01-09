package com.feb.order;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class OrderPostDto {

    @Positive
    private long memeberId;

    @Valid
    private List<OrderCoffeeDto> orderCoffees;
}
