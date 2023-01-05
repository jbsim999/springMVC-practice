package com.feb.coffee.service;

import com.feb.coffee.entity.Coffee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {

    public Coffee createCoffee(Coffee coffee){
        Coffee createdCoffee = coffee;
        return createdCoffee;
    }

    public Coffee updateCoffee(Coffee coffee){
        return coffee;
    }

    public Coffee findCoffee(long coffeeId){
        return new Coffee(coffeeId,"아메리카노", "Americano", 2500);
    }

    public List<Coffee> findCoffees(){
        return List.of(
                new Coffee(1L, "아메리카노", "Americano", 2500),
                new Coffee(2L, "카라멜라떼", "CaramelLatte",5000)
        );
    }

    public void deleteCoffee(long coffeeId){

    }
}
