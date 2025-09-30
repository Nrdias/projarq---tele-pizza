package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.SubmitOrderRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.SubmitOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order.OrderProcessingService;

@Component
public class SubmitOrderUseCase {
    private OrderProcessingService orderProcessingService;

    @Autowired
    public SubmitOrderUseCase(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    public SubmitOrderResponse run(SubmitOrderRequest request) {
        List<SubmitOrderRequest.OrderItemRequest> items = request.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }
        List<OrderProcessingService.OrderItemRequest> orderItems = items.stream()
            .map(item -> new OrderProcessingService.OrderItemRequest(item.getProductId(), item.getQuantity()))
            .collect(Collectors.toList());

        OrderProcessingService.OrderProcessingResult result = 
            orderProcessingService.processOrder(request.getCustomerCpf(), orderItems);

        if (result.isApproved() && result.getOrder() != null) {
            return new SubmitOrderResponse(
                true,
                result.getMessage(),
                result.getOrder(),
                result.getOrder().getValue(),
                result.getOrder().getTaxes(),
                result.getOrder().getDiscount(),
                result.getOrder().getChargedValue()
            );
        } else {
            return new SubmitOrderResponse(
                false,
                result.getMessage(),
                null,
                0.0,
                0.0,
                0.0,
                0.0
            );
        }
    }
}
