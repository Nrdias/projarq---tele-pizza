package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.PayOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order.OrderService;


@Component
public class PayOrderUseCase {

    private final OrderService orderService;

    @Autowired
    public PayOrderUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public PayOrderResponse run(long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            
            if (order == null) {
                return new PayOrderResponse(false, "Pedido não encontrado", orderId, null);
            }
            
            if (order.getStatus() != Order.Status.APROVADO) {
                return new PayOrderResponse(false, 
                    "Apenas pedidos aprovados podem ser pagos. Status atual: " + order.getStatus(), 
                    orderId, order.getStatus());
            }
            
            if (order.getPaymentDateTime() != null) {
                return new PayOrderResponse(false, "Pedido já foi pago anteriormente", orderId, order.getStatus());
            }
            
            LocalDateTime paymentDateTime = LocalDateTime.now();
            boolean paymentProcessed = orderService.processPayment(orderId, paymentDateTime);
            
            if (!paymentProcessed) {
                return new PayOrderResponse(false, "Erro ao processar pagamento", orderId, order.getStatus());
            }
            
            return new PayOrderResponse(true, "Pagamento processado com sucesso. Pedido encaminhado para a cozinha.", 
                orderId, Order.Status.PAGO);
                
        } catch (Exception e) {
            return new PayOrderResponse(false, "Erro interno ao processar pagamento: " + e.getMessage(), 
                orderId, null);
        }
    }
}