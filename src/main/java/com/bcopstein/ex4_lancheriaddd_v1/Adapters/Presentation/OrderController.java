package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.order.OrderPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.order.SubmitOrderPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.CancelOrderUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.CustomerNotFoundException;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetCustomerDeliveredOrdersBetweenDatesUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetDeliveredOrdersBetweenDatesUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetOrderStatusUsecase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.GetOrderUsecase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.PayOrderUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Order.SubmitOrderUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.CancelOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.CustomerDeliveredOrdersBetweenDatesResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.DeliveredOrdersBetweenDatesResponse;
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
    private GetDeliveredOrdersBetweenDatesUseCase getDeliveredOrdersBetweenDatesUC;
    private GetCustomerDeliveredOrdersBetweenDatesUseCase getCustomerDeliveredOrdersBetweenDatesUC;

    public OrderController(GetOrderUsecase getOrderUseCase, 
                          GetOrderStatusUsecase getOrderStatusUseCase,
                          SubmitOrderUseCase submitOrderUC,
                          CancelOrderUseCase cancelOrderUC,
                          PayOrderUseCase payOrderUC,
                          GetDeliveredOrdersBetweenDatesUseCase getDeliveredOrdersBetweenDatesUC,
                          GetCustomerDeliveredOrdersBetweenDatesUseCase getCustomerDeliveredOrdersBetweenDatesUC){
      this.getOrderUseCase = getOrderUseCase;
      this.getOrderStatusUseCase = getOrderStatusUseCase;
      this.submitOrderUC = submitOrderUC;
      this.cancelOrderUC = cancelOrderUC;
      this.payOrderUC = payOrderUC;
      this.getDeliveredOrdersBetweenDatesUC = getDeliveredOrdersBetweenDatesUC;
      this.getCustomerDeliveredOrdersBetweenDatesUC = getCustomerDeliveredOrdersBetweenDatesUC;
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<OrderPresenter> getOrder(@PathVariable(value="id")long id){
      Order order = getOrderUseCase.run(id);
      if (order == null) {
        return ResponseEntity.notFound().build();
      }
      
      List<OrderPresenter.OrderItemPresenter> itensPresenter = order.getItems().stream()
        .map(item -> new OrderPresenter.OrderItemPresenter(
          item.getItem().getId(),
          item.getItem().getDescription(),
          item.getItem().getPrice(),
          item.getQuantity()
        ))
        .collect(Collectors.toList());
      
      OrderPresenter presenter = new OrderPresenter(
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
      
      return ResponseEntity.ok(presenter);
    }

    @GetMapping("/{id}/status")
    @CrossOrigin("*")
    public ResponseEntity<Order.Status> getOrderStatus(@PathVariable(value="id")long id){
      try {
        Order.Status status = getOrderStatusUseCase.run(id);
        if (status == null) {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(status);
      } catch (Exception e) {
        return ResponseEntity.notFound().build();
      }
    }

    @PostMapping
    @CrossOrigin("*")
    public ResponseEntity<SubmitOrderPresenter> submitOrder(@RequestBody SubmitOrderRequest request) {
      SubmitOrderResponse response = submitOrderUC.run(request);
      SubmitOrderPresenter presenter = new SubmitOrderPresenter(response);
      
      if (response.isApproved()) {
        return ResponseEntity.status(HttpStatus.CREATED).body(presenter);
      } else {
        String message = response.getMessage().toLowerCase();
        HttpStatus status;
        
        if (message.contains("não encontrado") || message.contains("not found")) {
          status = HttpStatus.NOT_FOUND; 
        } else if (message.contains("inválido") || message.contains("invalid") ||
                  message.contains("obrigatório") || message.contains("required")) {
          status = HttpStatus.BAD_REQUEST; 
        } else {
          status = HttpStatus.UNPROCESSABLE_ENTITY; 
        }
        
        return ResponseEntity.status(status).body(presenter);
      }
    }

    @PutMapping("/{id}/cancel")
    @CrossOrigin("*")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@PathVariable(value="id") long id) {
      CancelOrderResponse response = cancelOrderUC.run(id);
      
      if (response.isSuccess()) {
        return ResponseEntity.ok(response);
      } else {
        String message = response.getMessage().toLowerCase();
        HttpStatus status;
        
        if (message.contains("não encontrado") || message.contains("not found")) {
          status = HttpStatus.NOT_FOUND; 
        } else if (message.contains("já está cancelado") || message.contains("already cancelled") ||
                  message.contains("não é possível cancelar") || message.contains("cannot cancel")) {
          status = HttpStatus.CONFLICT; 
        } else {
          status = HttpStatus.INTERNAL_SERVER_ERROR; 
        }
        
        return ResponseEntity.status(status).body(response);
      }
    }
    
    @PutMapping("/{id}/pay")
    @CrossOrigin("*")
    public ResponseEntity<PayOrderResponse> payOrder(@PathVariable(value="id") long id) {
      PayOrderResponse response = payOrderUC.run(id);
      
      if (response.isSuccess()) {
        return ResponseEntity.ok(response);
      } else {
        String message = response.getMessage().toLowerCase();
        HttpStatus status;
        
        if (message.contains("não encontrado") || message.contains("not found")) {
          status = HttpStatus.NOT_FOUND; 
        } else if (message.contains("já foi pago") || message.contains("already paid") ||
                  message.contains("apenas pedidos aprovados") || message.contains("only approved orders")) {
          status = HttpStatus.CONFLICT; 
        } else if (message.contains("erro ao processar") || message.contains("error processing")) {
          status = HttpStatus.UNPROCESSABLE_ENTITY; 
        } else {
          status = HttpStatus.INTERNAL_SERVER_ERROR; 
        }
        
        return ResponseEntity.status(status).body(response);
      }
    }

    @GetMapping("/delivered")
    @CrossOrigin("*")
    public ResponseEntity<DeliveredOrdersBetweenDatesResponse> getDeliveredOrdersBetweenDates(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            
            List<Order> orders = getDeliveredOrdersBetweenDatesUC.run(startDateTime, endDateTime);
            DeliveredOrdersBetweenDatesResponse response = new DeliveredOrdersBetweenDatesResponse(startDate, endDate, orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            DeliveredOrdersBetweenDatesResponse errorResponse = new DeliveredOrdersBetweenDatesResponse("Error retrieving delivered orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/delivered/customer/{cpf}")
    @CrossOrigin("*")
    public ResponseEntity<CustomerDeliveredOrdersBetweenDatesResponse> getCustomerDeliveredOrdersBetweenDates(
            @PathVariable("cpf") String customerCpf,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            
            List<Order> orders = getCustomerDeliveredOrdersBetweenDatesUC.run(customerCpf, startDateTime, endDateTime);
            CustomerDeliveredOrdersBetweenDatesResponse response = new CustomerDeliveredOrdersBetweenDatesResponse(customerCpf, startDate, endDate, orders);
            return ResponseEntity.ok(response);
        } catch (CustomerNotFoundException e) {
            CustomerDeliveredOrdersBetweenDatesResponse errorResponse = new CustomerDeliveredOrdersBetweenDatesResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            CustomerDeliveredOrdersBetweenDatesResponse errorResponse = new CustomerDeliveredOrdersBetweenDatesResponse("Error retrieving customer delivered orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
