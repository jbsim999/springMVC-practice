package com.feb.coffee.mapper;

import com.feb.coffee.dto.CoffeePatchDto;
import com.feb.coffee.dto.CoffeePostDto;
import com.feb.coffee.dto.CoffeeResponseDto;
import com.feb.coffee.entity.Coffee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {
    Coffee coffeePostDtoToCoffee(CoffeePostDto coffeePostDto);
    Coffee coffeePatchDtoToCoffee(CoffeePatchDto coffeePatchDto);
    CoffeeResponseDto coffeeToCoffeeResponseDto(Coffee coffee);
    List<CoffeeResponseDto> coffeesToCoffeeResponseDto(List<Coffee> coffees);

}
