package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer.CustomerRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Products.ProductsRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Stock.StockRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Ingredient;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.OrderItem;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Product;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Recipe;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.StockItem;

@Service
public class OrderProcessingService {
    private CustomerRepository customerRepository;
    private ProductsRepository productsRepository;
    private StockRepository stockRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderProcessingService(CustomerRepository customerRepository,
                                ProductsRepository productsRepository,
                                StockRepository stockRepository,
                                OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.productsRepository = productsRepository;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
    }

    public OrderProcessingResult processOrder(String customerCpf, List<OrderItemRequest> items) {
        try {
            Customer customer = customerRepository.getCustomerByCpf(customerCpf);
            if (customer == null) {
                return new OrderProcessingResult(false, "Cliente não encontrado", null);
            }

            List<OrderItem> orderItems = new ArrayList<>();
            double totalValue = 0.0;

            for (OrderItemRequest itemRequest : items) {
                Product product = productsRepository.getProductById(itemRequest.getProductId());
                if (product == null) {
                    return new OrderProcessingResult(false, "Produto não encontrado: " + itemRequest.getProductId(), null);
                }

                OrderItem orderItem = new OrderItem(product, itemRequest.getQuantity());
                orderItems.add(orderItem);
                totalValue += (product.getPrice() * itemRequest.getQuantity()) / 100.0;
            }

            Map<Long, Integer> requiredIngredients = calculateRequiredIngredients(orderItems);
            if (!checkIngredientAvailability(requiredIngredients)) {
                return new OrderProcessingResult(false, "Ingredientes insuficientes no estoque", null);
            }

            double taxes = totalValue * 0.15;
            double discount = calculateDiscount(totalValue);
            double chargedValue = totalValue + taxes - discount;

            long orderId = orderRepository.getNextOrderId();
            Order order = new Order(
                orderId,
                customer,
                null, // Payment date will be set when paid
                orderItems,
                Order.Status.APROVADO,
                totalValue,
                taxes,
                discount,
                chargedValue
            );

            // 6. Reserve ingredients (update stock)
            reserveIngredients(requiredIngredients);

            // 7. Save order
            Order savedOrder = orderRepository.createOrder(order);

            return new OrderProcessingResult(true, "Pedido aprovado com sucesso", savedOrder);

        } catch (Exception e) {
            return new OrderProcessingResult(false, "Erro ao processar pedido: " + e.getMessage(), null);
        }
    }

    private Map<Long, Integer> calculateRequiredIngredients(List<OrderItem> orderItems) {
        Map<Long, Integer> requiredIngredients = new java.util.HashMap<>();
        
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getItem();
            Recipe recipe = product.getRecipe();
            
            for (Ingredient ingredient : recipe.getIngredients()) {
                int currentRequired = requiredIngredients.getOrDefault(ingredient.getId(), 0);
                int additionalRequired = orderItem.getQuantity();
                requiredIngredients.put(ingredient.getId(), currentRequired + additionalRequired);
            }
        }
        
        return requiredIngredients;
    }

    private boolean checkIngredientAvailability(Map<Long, Integer> requiredIngredients) {
        for (Map.Entry<Long, Integer> entry : requiredIngredients.entrySet()) {
            long ingredientId = entry.getKey();
            int requiredQuantity = entry.getValue();
            
            StockItem stockItem = stockRepository.getStockItemByIngredientId(ingredientId);
            if (stockItem == null || stockItem.getQuantidade() < requiredQuantity) {
                return false;
            }
        }
        return true;
    }

    private void reserveIngredients(Map<Long, Integer> requiredIngredients) {
        for (Map.Entry<Long, Integer> entry : requiredIngredients.entrySet()) {
            long ingredientId = entry.getKey();
            int requiredQuantity = entry.getValue();
            
            StockItem stockItem = stockRepository.getStockItemByIngredientId(ingredientId);
            int newQuantity = stockItem.getQuantidade() - requiredQuantity;
            stockRepository.updateStockItem(ingredientId, newQuantity);
        }
    }

    private double calculateDiscount(double totalValue) {
        if (totalValue >= 100.0) {
            return 10.0; // 10 reais discount for orders >= 100
        }
        return 0.0;
    }

    public static class OrderItemRequest {
        private long productId;
        private int quantity;

        public OrderItemRequest() {}

        public OrderItemRequest(long productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class OrderProcessingResult {
        private boolean approved;
        private String message;
        private Order order;

        public OrderProcessingResult(boolean approved, String message, Order order) {
            this.approved = approved;
            this.message = message;
            this.order = order;
        }

        public boolean isApproved() {
            return approved;
        }

        public String getMessage() {
            return message;
        }

        public Order getOrder() {
            return order;
        }
    }
}
