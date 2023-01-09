package com.feb.order;

import com.feb.coffee.service.CoffeeService;
import com.feb.exception.BusinessLogicException;
import com.feb.exception.ExceptionCode;
import com.feb.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    final private OrderRepository orderRepository;
    final private MemberService memberService;
    final private CoffeeService coffeeService;

    public OrderService(OrderRepository orderRepository, MemberService memberService, CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.coffeeService = coffeeService;
    }

    public Order createOrder(Order order){

        memberService.findVerifiedMember(order.getMemberId().getId());

        order.getOrderCoffees()
                .stream()
                .forEach(coffeeRef ->{
                    coffeeService.findVerifiedCoffee(coffeeRef.getCoffeeId());
                });
        return orderRepository.save(order);
    }

    public Order findOrder(long orderId){
        return findVerifiedOrder(orderId);
    }

    public List<Order> findOrders(){
        return (List<Order>) orderRepository.findAll();
    }

    public void cancelOrder(long orderId){
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();

        if (step >= 2)throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);

        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        orderRepository.save(findOrder);
    }

    private Order findVerifiedOrder(long orderId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder =
                optionalOrder.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }
}
