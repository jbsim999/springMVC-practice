package com.feb.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feb.coffee.service.CoffeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final static String ORDER_DEFAULT_URL = "/v1/orders";
    private final OrderService orderService;
    private final OrderMapper mapper;
    private final CoffeeService coffeeService;

    public OrderController(OrderService orderService, OrderMapper mapper, CoffeeService coffeeService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.coffeeService = coffeeService;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto){
        Order order = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));

        URI location =
                UriComponentsBuilder
                        .newInstance()
                        .path(ORDER_DEFAULT_URL + "{order-id}")
                        .buildAndExpand(order.getOrderId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id")@Positive long orderId){
        Order order = orderService.findOrder(orderId);

        return new ResponseEntity<>(mapper.orderToOrderResponseDto(coffeeService,order), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(){
        List<Order> orders = orderService.findOrders();

        List<OrderResponseDto> response =
                orders.stream()
                        .map(order -> mapper.orderToOrderResponseDto(coffeeService,order))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") @Positive long orderId){
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
