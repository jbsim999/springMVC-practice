package com.codestates.order;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity postOrder(@RequestParam("memberId")long memberId,
                                    @RequestParam("coffeeId")long coffeeId){
//        System.out.println("# memberId : " + memberId);
//        System.out.println("# coffeeId : " + coffeeId);
//
//        String response =
//                "{\"" +
//                        "memberId\":\""+memberId+"\"," +
//                        "\"coffeeId\":\""+coffeeId+"\"" +
//                        "}";
        Map<String , Integer> map = new HashMap<>();

        map.put("memberId", (int) memberId);
        map.put("coffeeId", (int) coffeeId);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("orderId")long orderId){
        System.out.println("# orderId : " + orderId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(){
        System.out.println("# get orders");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
