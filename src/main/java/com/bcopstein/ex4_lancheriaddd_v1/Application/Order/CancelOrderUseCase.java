package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.CancelOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order.OrderService;

@Component
public class CancelOrderUseCase {

    private OrderService orderService;

    @Autowired
    public CancelOrderUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public CancelOrderResponse run(long id) {
        Order order = orderService.getOrder(id);

        if (order == null) {
            return new CancelOrderResponse(false, "Pedido não encontrado", id);
        }

        if (order.getStatus() == Order.Status.CANCELADO) {
            return new CancelOrderResponse(false, "Pedido já está cancelado", id);
        }

        if (order.getPaymentDateTime() != null) {
            return new CancelOrderResponse(false, "Não é possível cancelar pedidos já pagos", id);
        }

        boolean success = orderService.cancelOrder(order);

        if (success) {
            return new CancelOrderResponse(true, "Pedido cancelado com sucesso", id);
        }

        return new CancelOrderResponse(false, "Erro ao cancelar o pedido", id);
    }
}