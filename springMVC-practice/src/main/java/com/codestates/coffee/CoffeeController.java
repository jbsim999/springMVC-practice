package com.codestates.coffee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/coffees")
public class CoffeeController {

//    @PostMapping
//    public ResponseEntity postCoffee(@RequestParam("korName")String korName,
//                                     @RequestParam("engName")String engName,
//                                     @RequestParam("price")int price){
////        System.out.println("# korName : "+ korName);
////        System.out.println("# engName : "+ engName);
////        System.out.println("# price : "+ price);
////
////        String response =
////                "{\"" +
////                        "korName\":\""+korName+"\"," +
////                        "\"engName\":\""+engName+"\",\"" +
////                        "price\":\"" + price+
////                        "\"}";
//        Map<String, String> map = new HashMap<>();
//
//        map.put("korName",korName);
//        map.put("engName",engName);
//        map.put("price", String.valueOf(price));
//
//        return new ResponseEntity<>(map, HttpStatus.CREATED);
//    }
    private final Map<Long, Map<String, Object>> coffees = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, Object> coffee1 = new HashMap<>();
        long coffeeId = 1L;
        coffee1.put("coffeeId", coffeeId);
        coffee1.put("korName", "바닐라 라떼");
        coffee1.put("engName", "Vanilla Latte");
        coffee1.put("price", 4500);

        coffees.put(coffeeId, coffee1);
    }

    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@PathVariable("coffee-id")long coffeeId,
                                      @RequestParam("korName")String korName,
                                      @RequestParam("price")int price){
        coffees.get(coffeeId).put("korName",korName);
        coffees.get(coffeeId).put("price",price);

        return new ResponseEntity<>(coffees.get(coffeeId),HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id")long coffeeId){
        System.out.println("# coffeeId : "+coffeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees(){
        System.out.println("# get coffees");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(@PathVariable("coffee-id")long coffeeId){
        coffees.remove(coffeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
