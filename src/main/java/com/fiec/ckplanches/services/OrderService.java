package com.fiec.ckplanches.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fiec.ckplanches.DTO.DeliveryDTO;
import com.fiec.ckplanches.DTO.DeliveryTableDTO;
import com.fiec.ckplanches.DTO.OrderDTO;
import com.fiec.ckplanches.DTO.OrderProductDTO;
import com.fiec.ckplanches.DTO.OrderProductTableDTO;
import com.fiec.ckplanches.DTO.OrderTableDTO;
import com.fiec.ckplanches.DTO.ProductTableDTO;
import com.fiec.ckplanches.DTO.SupplyTableDTO;
import com.fiec.ckplanches.model.delivery.Delivery;
import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.productOrder.ProductOrder;
import com.fiec.ckplanches.model.productSupply.ProductSupply;
import com.fiec.ckplanches.model.supply.Supply;
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

    public OrderService(ProductOrderRepository productOrderRepository, ProductSupplyRepository productSupplyRepository, OrderRepository orderRepository, ProductRepository productRepository){
        this.productOrderRepository = productOrderRepository;
        this.productSupplyRepository = productSupplyRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    
    public List<OrderTableDTO> listarPedidos(List<Order> orders){
        List<OrderTableDTO> orderDTOs = new ArrayList<>();

        for (Order element: orders) {
            List<ProductTableDTO> productDTOs = new ArrayList<>();
            List<ProductOrder> productOrders = productOrderRepository.findByOrder(element);
            
     
            for (ProductOrder productOrder : productOrders) {
                Product product = productOrder.getProduct(); 
                productDTOs.add(convertProductToTableDTO(product));
            }

            orderDTOs.add(convertOrderToTableDTO(element));

        }
        return orderDTOs;
    }

    public OrderTableDTO criarPedido(OrderDTO orderDTO, DeliveryDTO deliveryDTO){
        Delivery delivery = null;
        if(deliveryDTO != null) delivery = modificarDelivery(new Delivery(), deliveryDTO);
        Order order = modificarOrder(new Order(), orderDTO, delivery);
        order = orderRepository.save(order);
        criarProductOrder(order, orderDTO);
        order.setTotalValue(calcularValorTotal(order.getProductOrders()));
        order = orderRepository.save(order);
        return convertOrderToTableDTO(orderRepository.findById(order.getOrderId()).orElse(order));
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
            supplyTableDTOs  
        ));
    }

    // Order

    public OrderTableDTO convertOrderToTableDTO(Order order){
        List<OrderProductTableDTO> orderProductTableDTOs = new ArrayList<>();
        double totalValue = 0;
        List<ProductOrder> productOrders = productOrderRepository.findByOrder(order);
            
        for(ProductOrder productOrder : productOrders) {
                Product product = productOrder.getProduct(); 
                OrderProductTableDTO orderProductTableDTO = new OrderProductTableDTO(
                    convertProductToTableDTO(product), 
                    productOrder.getQuantidade(), 
                    productOrder.getPreco(), 
                    productOrder.getObservacao());
                orderProductTableDTOs.add(orderProductTableDTO);
                totalValue += productOrder.getPreco();
        }

        order.setTotalValue(totalValue);

        

        return new OrderTableDTO(
            order.getOrderId(),
            order.getOrderStatus(),
            order.getCustomerName(),
            order.getExitMethod(),
            order.getPaymentMethod(),
            order.getTotalValue(),
            orderProductTableDTOs,
            convertDeliveryToTableDTO(order.getDelivery()));
    }

    public Order modificarOrder(Order order, OrderDTO orderDTO, Delivery delivery){
        order.setOrderStatus(orderDTO.orderStatus());
        order.setCustomerName(orderDTO.customerName());
        order.setExitMethod(orderDTO.exitMethod());
        order.setPaymentMethod(orderDTO.paymentMethod());
        if(delivery != null)order.setDelivery(delivery);
        order.setExitDatetime(orderDTO.exitDateTime());
        order.setEndDatetime(orderDTO.endDateTime());
        return order;
    }

    public void criarProductOrder(Order order, OrderDTO orderDTO){
        List<ProductOrder> productOrders = order.getProductOrders();
    
        for(OrderProductDTO orderProductDTO : orderDTO.orderProductDTOs()){
            ProductOrder productOrder = new ProductOrder();
            Product product = Optional.ofNullable(productRepository.findByProductName(orderProductDTO.productName()))
                        .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
            productOrder.setObservacao(orderProductDTO.observacao());
            productOrder.setOrder(order);
            productOrder.setProduct(product);
            productOrder.setQuantidade(orderProductDTO.quantity());
            
            productOrders.add(productOrder);  // Adiciona o ProductOrder à lista
            productOrderRepository.save(productOrder);
        }
    
        // Atualizando a lista de ProductOrders na ordem
        order.setProductOrders(productOrders);
    }

    public Double calcularValorTotal(List<ProductOrder> productOrders){
        //System.out.println(productOrders.size());
        double totalValue = 0;
        //if(productOrders != null)
        for(ProductOrder productOrder : productOrders) {
                totalValue += productOrder.getPreco();
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
            supply.getMaxQuantity(), 
            null);
    }

    // Delivery

    public Delivery modificarDelivery(Delivery delivery, DeliveryDTO deliveryDTO){
        delivery.setMotoboy(deliveryDTO.motoboy());
        delivery.setAddress(deliveryDTO.address());
        delivery.setComplement(deliveryDTO.complement());
        delivery.setChange(deliveryDTO.change());
        delivery.setFee(deliveryDTO.fee());
        return delivery;
    }

    public DeliveryTableDTO convertDeliveryToTableDTO(Delivery delivery){
        return new DeliveryTableDTO(
        delivery.getDeliveryId(),
        delivery.getMotoboy(), 
        delivery.getAddress(), 
        delivery.getComplement(), 
        delivery.getChange(), 
        delivery.getFee());
    }

}
