package com.feb.coffee.controller;

import com.feb.coffee.dto.CoffeeResponseDto;
import com.feb.coffee.service.CoffeeService;
import com.feb.coffee.dto.CoffeePatchDto;
import com.feb.coffee.dto.CoffeePostDto;
import com.feb.coffee.entity.Coffee;
import com.feb.coffee.mapper.CoffeeMapper;
import com.feb.coffee.dto.CoffeePatchDto;
import com.feb.coffee.dto.CoffeePostDto;
import com.feb.coffee.entity.Coffee;
import com.feb.coffee.mapper.CoffeeMapper;
import com.feb.coffee.service.CoffeeService;
import com.feb.member.entity.Member;
import com.feb.response.MultiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/coffees")
@Validated
public class CoffeeController {
    private final CoffeeService coffeeService;
    private final CoffeeMapper coffeeMapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper coffeeMapper) {
        this.coffeeService = coffeeService;
        this.coffeeMapper = coffeeMapper;
    }

    @PostMapping
    public ResponseEntity postCoffee(@Valid @RequestBody CoffeePostDto coffeePostDto){

        Coffee response = coffeeService.createCoffee(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        return new ResponseEntity<>(coffeeMapper.coffeeToCoffeeResponseDto(response), HttpStatus.CREATED);
    }



    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@PathVariable("coffee-id")@Positive long coffeeId,
                                      @Valid @RequestBody CoffeePatchDto coffeePatchDto){
        coffeePatchDto.setCoffeeId(coffeeId);
        Coffee response = coffeeService.updateCoffee(coffeeMapper.coffeePatchDtoToCoffee(coffeePatchDto));

        return new ResponseEntity<>(coffeeMapper.coffeeToCoffeeResponseDto(response),HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id")long coffeeId){
        Coffee response = coffeeService.findCoffee(coffeeId);
        return new ResponseEntity<>(coffeeMapper.coffeeToCoffeeResponseDto(response),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size){
        Page<Coffee> pageCoffees = coffeeService.findCoffees(page-1,size);
        List<Coffee> coffees = pageCoffees.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(coffeeMapper.coffeesToCoffeeResponseDto(coffees)
                ,pageCoffees),HttpStatus.OK);
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(@PathVariable("coffee-id")@Positive long coffeeId){
        coffeeService.deleteCoffee(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
