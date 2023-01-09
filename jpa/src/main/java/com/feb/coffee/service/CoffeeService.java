package com.feb.coffee.service;

import com.feb.coffee.entity.Coffee;
import com.feb.coffee.repository.CoffeeRepository;
import com.feb.exception.BusinessLogicException;
import com.feb.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {

    private CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee createCoffee(Coffee coffee){
        String coffeeCode = coffee.getCoffeeCode().toUpperCase();

        verifyExistCoffee(coffeeCode);
        coffee.setCoffeeCode(coffeeCode);

        return coffeeRepository.save(coffee);
    }

    public Coffee updateCoffee(Coffee coffee){
        Coffee findCoffee = findVerifiedCoffee(coffee.getCoffeeId());

        Optional.ofNullable(coffee.getKorName())
                .ifPresent(korName -> findCoffee.setKorName(korName));
        Optional.ofNullable(coffee.getEngName())
                .ifPresent(engName -> findCoffee.setEngName(engName));
        Optional.ofNullable(coffee.getPrice())
                .ifPresent(price -> findCoffee.setPrice(price));

        return coffeeRepository.save(findCoffee);
    }

    public Coffee findCoffee(long coffeeId){
        return findVerifiedCoffee(coffeeId);
    }

    public List<Coffee> findCoffees(){
        return (List<Coffee>) coffeeRepository.findAll();
    }

    public void deleteCoffee(long coffeeId){
        Coffee coffee = findVerifiedCoffee(coffeeId);
        coffeeRepository.delete(coffee);
    }

    public Coffee findVerifiedCoffee(long coffeeId){
        Optional<Coffee> optionalCoffee = coffeeRepository.findById(coffeeId);
        Coffee findCoffee =
                optionalCoffee.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));
        return findCoffee;
    }

    private void verifyExistCoffee(String coffeeCode){
        Optional<Coffee> coffee = coffeeRepository.findByCoffeeCode(coffeeCode);
        if (coffee.isPresent()) throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
    }

    private Coffee findVerifiedCoffeeByQuery(long coffeeId){
        Optional<Coffee> optionalCoffee = coffeeRepository.findByCoffee(coffeeId);
        Coffee findCoffee =
                optionalCoffee.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findCoffee;
    }
}
