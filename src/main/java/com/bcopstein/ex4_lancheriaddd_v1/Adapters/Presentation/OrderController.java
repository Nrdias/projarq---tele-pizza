package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.order.OrderPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.order.SubmitOrderPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.CancelOrderUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetOrderStatusUsecase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetOrderUsecase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.PayOrderUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.SubmitOrderUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.CancelOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.PayOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.SubmitOrderRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.SubmitOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@RestController
@RequestMapping("/pedido")
public class OrderController {

    private GetOrderUsecase getOrderUseCase;
    private GetOrderStatusUsecase getOrderStatusUseCase;
    private SubmitOrderUseCase submitOrderUC;
    private CancelOrderUseCase cancelOrderUC;
    private PayOrderUseCase payOrderUC;

    public OrderController(GetOrderUsecase getOrderUseCase, 
                          GetOrderStatusUsecase getOrderStatusUseCase,
                          SubmitOrderUseCase submitOrderUC,
                          CancelOrderUseCase cancelOrderUC,
                          PayOrderUseCase payOrderUC){
      this.getOrderUseCase = getOrderUseCase;
      this.getOrderStatusUseCase = getOrderStatusUseCase;
      this.submitOrderUC = submitOrderUC;
      this.cancelOrderUC = cancelOrderUC;
      this.payOrderUC = payOrderUC;
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

    @PostMapping
    @CrossOrigin("*")
    public SubmitOrderPresenter submitOrder(@RequestBody SubmitOrderRequest request) {
      SubmitOrderResponse response = submitOrderUC.run(request);
      return new SubmitOrderPresenter(response);
    }

    @PutMapping("/{id}/cancel")
    @CrossOrigin("*")
    public CancelOrderResponse cancelOrder(@PathVariable(value="id") long id) {
      return cancelOrderUC.run(id);
    }
    
    @PutMapping("/{id}/pay")
    @CrossOrigin("*")
    public PayOrderResponse payOrder(@PathVariable(value="id") long id) {
      return payOrderUC.run(id);
    }
}
