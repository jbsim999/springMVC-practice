package com.feb.order;

import com.feb.coffee.entity.Coffee;
import com.feb.coffee.service.CoffeeService;
import org.mapstruct.Mapper;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import javax.swing.text.html.CSS;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    default Order orderPostDtoToOrder(OrderPostDto orderPostDto){
        Order order = new Order();
        order.setMemberId(
                new AggregateReference.IdOnlyAggregateReference<>(orderPostDto.getMemeberId())
        );
        Set<CoffeeRef> orderCoffees = orderPostDto.getOrderCoffees()
                .stream()
                .map(orderCoffeeDto ->
                        CoffeeRef.builder()
                                .coffeeId(orderCoffeeDto.getCoffeeId())
                                .quantity(orderCoffeeDto.getQuantity())
                                .build())
                .collect(Collectors.toSet());
        order.setOrderCoffees(orderCoffees);

        return order;
    }

    default OrderResponseDto orderToOrderResponseDto(CoffeeService coffeeService, Order order){

        long memberId = order.getMemberId().getId();

        List<OrderCoffeeResponseDto> orderCoffees =
                orderToOrderCoffeeResponseDto(coffeeService, order.getOrderCoffees());

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderCoffees(orderCoffees);
        orderResponseDto.setMemberId(memberId);
        orderResponseDto.setCreatedAt(order.getCreatedAt());
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setOrderStatus(order.getOrderStatus());

        return orderResponseDto;
    }

    default List<OrderCoffeeResponseDto> orderToOrderCoffeeResponseDto(CoffeeService coffeeService,
                                                                       Set<CoffeeRef> orderCoffees){
        return orderCoffees.stream()
                .map(coffeeRef -> {
                    Coffee coffee = coffeeService.findCoffee(coffeeRef.getCoffeeId());

                    return new OrderCoffeeResponseDto(coffee.getCoffeeId(),
                            coffee.getKorName(),
                            coffee.getEngName(),
                            coffee.getPrice(),
                            coffeeRef.getQuantity());
                }).collect(Collectors.toList());
    }
}
