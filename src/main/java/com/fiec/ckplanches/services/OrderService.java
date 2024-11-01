package com.fiec.ckplanches.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.DeliveryDTO;
import com.fiec.ckplanches.DTO.OrderDTO;
import com.fiec.ckplanches.DTO.OrderProductDTO;
import com.fiec.ckplanches.DTO.OrderProductTableDTO;
import com.fiec.ckplanches.DTO.OrderTableDTO;
import com.fiec.ckplanches.DTO.OrderUpdateDTO;
import com.fiec.ckplanches.DTO.ProductTableDTO;
import com.fiec.ckplanches.DTO.SupplyTableDTO;
import com.fiec.ckplanches.model.delivery.Delivery;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.productOrder.ProductOrder;
import com.fiec.ckplanches.model.productSupply.ProductSupply;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.repositories.DeliveryRepository;
import com.fiec.ckplanches.repositories.OrderRepository;
import com.fiec.ckplanches.repositories.ProductOrderRepository;
import com.fiec.ckplanches.repositories.ProductRepository;
import com.fiec.ckplanches.repositories.ProductSupplyRepository;

@Service
public class OrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductSupplyRepository productSupplyRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryService deliveryService;

    public OrderService(ProductOrderRepository productOrderRepository, ProductSupplyRepository productSupplyRepository, OrderRepository orderRepository, ProductRepository productRepository,
    DeliveryRepository deliveryRepository, DeliveryService deliveryService){
        this.productOrderRepository = productOrderRepository;
        this.productSupplyRepository = productSupplyRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.deliveryRepository = deliveryRepository;
        this.deliveryService = deliveryService;
    }
    
    public List<OrderTableDTO> listarPedidos(List<Order> orders){
        List<OrderTableDTO> orderDTOs = new ArrayList<>();

        for (Order element: orders) {
            orderDTOs.add(convertOrderToTableDTO(element));

        }
        return orderDTOs;
    }

    public OrderTableDTO criarPedido(OrderDTO orderDTO, DeliveryDTO deliveryDTO){
        Delivery delivery = null;
        if(deliveryDTO != null) {
            delivery = deliveryService.modificarDelivery(new Delivery(), deliveryDTO);
            delivery.setStatus(Status.ATIVO);
        }
        Order order = modificarOrder(new Order(), orderDTO, delivery);
        order = orderRepository.save(order);
        criarProductOrder(order, orderDTO.orderProductDTOs());
        order.setStatus(Status.ATIVO);
        order.setTotalValue(calcularValorTotal(order.getProductOrders()));
        order = orderRepository.save(order);
        return convertOrderToTableDTO(orderRepository.findById(order.getOrderId()).orElse(order));
    }

    public OrderTableDTO atualizarPedido(OrderUpdateDTO orderDTO){
        Order order = orderRepository.findById(orderDTO.id()).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        if(order != null){
            modificarOrder(order, orderDTO);
            order.setTotalValue(calcularValorTotal(order.getProductOrders()));
            Delivery delivery = order.getDelivery();
            if(delivery != null) {
                delivery = deliveryService.modificarDelivery(order.getDelivery(), orderDTO.deliveryDTO());
                deliveryRepository.save(delivery);
            }
            order.setDelivery(delivery);
            order = orderRepository.save(order);
        }
        return convertOrderToTableDTO(order);
    }

    public boolean deletarPedido(int id){
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            order.setStatus(Status.INATIVO);
            orderRepository.save(order);
            return true;
        }
        return false;
    }


    // Conversões

    // Product

    public ProductTableDTO convertProductToTableDTO(Product product){
        List<SupplyTableDTO> supplyTableDTOs = new ArrayList<>();
        List<ProductSupply> productSupplies = productSupplyRepository.findByProduct(product);

        for(ProductSupply productSupply:productSupplies){
            Supply supply = productSupply.getSupply();
            supplyTableDTOs.add(convertSupplyToTableDTO(supply));
        }

        return(new ProductTableDTO(
            product.getProduct_id(),
            product.getProductName(),
            product.getProduct_value(),
            product.getImagemUrl(), 
            product.getDescription(),
            product.getCategory(), 
            supplyTableDTOs  
        ));
    }

    // Order

    public OrderTableDTO convertOrderToTableDTO(Order order){
        List<OrderProductTableDTO> orderProductTableDTOs = new ArrayList<>();
        //double totalValue = 0;
        List<ProductOrder> productOrders = productOrderRepository.findByOrder(order);
            
        for(ProductOrder productOrder : productOrders) {
                Product product = productOrder.getProduct(); 
                OrderProductTableDTO orderProductTableDTO = new OrderProductTableDTO(
                    convertProductToTableDTO(product), 
                    productOrder.getQuantidade(), 
                    productOrder.getPreco(), 
                    productOrder.getObservacao());
                orderProductTableDTOs.add(orderProductTableDTO);
                //totalValue += productOrder.getPreco();
        }

        //order.setTotalValue(totalValue);

        

        return new OrderTableDTO(
            order.getOrderId(),
            order.getOrderStatus(),
            order.getCustomerName(),
            order.getExitMethod(),
            order.getPaymentMethod(),
            order.getTotalValue(),
            order.getEndDatetime(),
            orderProductTableDTOs,
            order.getDelivery() != null ? deliveryService.convertDeliveryToTableDTO(order.getDelivery()) : null
            );
    }

    public Order modificarOrder(Order order, OrderDTO orderDTO, Delivery delivery){
        if(orderDTO.orderStatus() != null) order.setOrderStatus(orderDTO.orderStatus());
        if(orderDTO.customerName() != null) order.setCustomerName(orderDTO.customerName());
        if(orderDTO.exitMethod() != null) order.setExitMethod(orderDTO.exitMethod());
        if(orderDTO.paymentMethod() != null) order.setPaymentMethod(orderDTO.paymentMethod());
        if(delivery != null) order.setDelivery(delivery);
        if(orderDTO.exitDateTime() != null) order.setExitDatetime(orderDTO.exitDateTime());
        if(orderDTO.endDateTime() != null) order.setEndDatetime(orderDTO.endDateTime());
        return order;
    }

    public Order modificarOrder(Order order, OrderUpdateDTO orderUpdateDTO){
        if(orderUpdateDTO.orderStatus() != null)order.setOrderStatus(orderUpdateDTO.orderStatus());
        if(orderUpdateDTO.customerName() != null)order.setCustomerName(orderUpdateDTO.customerName());
        if(orderUpdateDTO.exitMethod() != null)order.setExitMethod(orderUpdateDTO.exitMethod());
        if(orderUpdateDTO.paymentMethod() != null)order.setPaymentMethod(orderUpdateDTO.paymentMethod());
        if(orderUpdateDTO.endDateTime() != null)order.setEndDatetime(orderUpdateDTO.endDateTime());
        if(orderUpdateDTO.exitDateTime() != null)order.setExitDatetime(orderUpdateDTO.exitDateTime());
        removeProductOrders(order);
        criarProductOrder(order, orderUpdateDTO.orderProductDTOs());
        return order;
    }

    public ProductOrder convertOrderProductDTOtoProductOrder(OrderProductDTO orderProductDTO, Order order){
        ProductOrder productOrder = new ProductOrder();
        Product product = Optional.ofNullable(productRepository.findByProductName(orderProductDTO.productName()))
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        productOrder.setObservacao(orderProductDTO.observacao());
        productOrder.setOrder(order);
        productOrder.setProduct(product);
        productOrder.setQuantidade(orderProductDTO.quantity());
        return productOrder;
    }

    public void removeProductOrders(Order order) {
        // Inicializa a lista de ProductOrders, se ainda não estiver inicializada
        List<ProductOrder> productOrders = order.getProductOrders();
        
        // Copia a lista para evitar ConcurrentModificationException
        List<ProductOrder> ordersToRemove = new ArrayList<>(productOrders);
    
        for (ProductOrder productOrder : ordersToRemove) {
            if (productOrderRepository.existsById(productOrder.getId())) {
                productOrderRepository.deleteById(productOrder.getId());
            }
        }
    
        // Limpa a lista de ProductOrders após a remoção
        productOrders.clear();
    }
    
    public void criarProductOrder(Order order, List<OrderProductDTO> orderProductDTOs) {
        
        // Se não houver ProductOrders, inicialize a lista
        if (order.getProductOrders() == null) {
            order.setProductOrders(new ArrayList<>());
        }
    
        List<ProductOrder> productOrders = order.getProductOrders();
    
        for (OrderProductDTO orderProductDTO : orderProductDTOs) {
            Product product = productRepository.findByProductName(orderProductDTO.productName());
    
            // Verifica se o ProductOrder já existe para a ordem e produto
            ProductOrder productOrder = productOrderRepository.findByProductAndOrder(product, order);
    
            if (productOrder != null) {
                // Atualiza o ProductOrder existente
                productOrder.setObservacao(orderProductDTO.observacao());
                productOrder.setQuantidade(orderProductDTO.quantity());
                productOrderRepository.save(productOrder);
            } else {
                // Cria um novo ProductOrder
                ProductOrder productOrderNew = convertOrderProductDTOtoProductOrder(orderProductDTO, order);
                productOrders.add(productOrderNew);  // Adiciona o ProductOrder à lista
                productOrderRepository.save(productOrderNew);
            }
        }
    }
    
    

    public Double calcularValorTotal(List<ProductOrder> productOrders){
        //System.out.println(productOrders.size());
        double totalValue = 0;
        if(productOrders != null){
            for(ProductOrder productOrder : productOrders) {
                totalValue += productOrder.getPreco();
            }
        }
        return totalValue;
    }

    // Supply

    public SupplyTableDTO convertSupplyToTableDTO(Supply supply){
        return new SupplyTableDTO(
            supply.getId(),
            supply.getName(), 
            supply.getDescription(), 
            supply.getQuantity(), 
            supply.getMinQuantity(), 
            supply.getMaxQuantity());
    }

}
