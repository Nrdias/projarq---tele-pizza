package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Apresentacao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Apresentacao.Presenters.order.OrderPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetOrderStatusUsecase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetOrderUsecase;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@RestController
@RequestMapping("/pedido")
public class OrderController {

    private GetOrderUsecase getOrderUseCase;
    private GetOrderStatusUsecase getOrderStatusUseCase;

    public OrderController(GetOrderUsecase getOrderUseCase, GetOrderStatusUsecase getOrderStatusUseCase){
      this.getOrderUseCase = getOrderUseCase;
      this.getOrderStatusUseCase = getOrderStatusUseCase;
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public OrderPresenter getOrder(@PathVariable(value="id")long id){
      Order order = getOrderUseCase.run(id);
      if (order == null) {
        return null;
      }
      
      List<OrderPresenter.OrderItemPresenter> itensPresenter = order.getItems().stream()
        .map(item -> new OrderPresenter.OrderItemPresenter(
          item.getItem().getId(),
          item.getItem().getDescription(),
          item.getItem().getPrice(),
          item.getQuantity()
        ))
        .collect(Collectors.toList());
      
      return new OrderPresenter(
        order.getId(),
        order.getCustomer(),
        order.getPaymentDateTime(),
        itensPresenter,
        order.getStatus(),
        order.getValue(),
        order.getTaxes(),
        order.getDiscount(),
        order.getChargedValue()
      );
    }

    @GetMapping("/{id}/status")
    @CrossOrigin("*")
    public Order.Status getOrderStatus(@PathVariable(value="id")long id){
      return getOrderStatusUseCase.run(id);
    }
}
