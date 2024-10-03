package com.farmstory.service;

import com.farmstory.dto.OrderDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.OrderPageResponseDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.entity.Order;
import com.farmstory.entity.Product;
import com.farmstory.entity.User;
import com.farmstory.repository.order.OrderRepository;
import com.farmstory.repository.product.ProductRepository;
import com.farmstory.repository.user.UserRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public int insertOrder(OrderDTO orderDTO) {


        Order order = modelMapper.map(orderDTO, Order.class);

        User user = userRepository.findById(orderDTO.getOrderuserUid()).orElseThrow();
        Product product = productRepository.findById(orderDTO.getProdNo()).orElseThrow();

        order.setUser(user);
        order.setProduct(product);
        log.info(order);
        userRepository.updateUserPoint(orderDTO.getOrderUsePoint(),orderDTO.getOrderPlusPoint(),orderDTO.getOrderuserUid());
        log.info(user);

        userRepository.save(user);

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getOrderNo();

    }
    public OrderDTO selectOrderbyId(String orderno){
        int ordernumber = Integer.parseInt(orderno);
        Optional<Order> order = orderRepository.findById(ordernumber);
        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> selectOrders() {
        List<Order> orders = orderRepository.findAll();
        log.info("orders"+orders);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(entity -> {
                    OrderDTO dto = entity.toDTO();
                    if (entity.getProduct() != null) {
                        dto.setProdName(entity.getProduct().getProdName()); // Product 이름 설정
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        for(OrderDTO order : orderDTOs) {
            LocalDateTime orderDateTime = order.getOrderDate();
            if(orderDateTime != null) {
                String fullDateTime = orderDateTime.toString();
                String[] split = fullDateTime.split("T");
                if(split.length == 2) {
                    order.setDate(split[0]);
                    order.setTimeDate(split[1]);
                }
            }
        }
        return orderDTOs;
    }
    public PageResponseDTO<OrderDTO> selectorderAll (PageRequestDTO pageRequestDTO) {
        int a = 0;
        Pageable pageable = pageRequestDTO.getPageable("orderNo",false);
        Page<Tuple> pageOrder = orderRepository.selectOrderAllForList(pageRequestDTO, pageable);
        List<OrderDTO> orderList = pageOrder.getContent().stream().map(tuple -> {
            Order order = tuple.get(0, Order.class);
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            if(order.getProduct() != null) {
                orderDTO.setProdName(order.getProduct().getProdName());
                orderDTO.setProdNo(order.getProduct().getProdNo());
                orderDTO.setProductDTO(modelMapper.map(productRepository.selectProduct(order.getProduct().getProdNo()), ProductDTO.class));
            }
            LocalDateTime orderDateTime = order.getOrderDate();
            if (orderDateTime != null) {
                orderDTO.setDate(orderDateTime.toLocalDate().toString());  // 날짜 부분
                orderDTO.setTimeDate(orderDateTime.toLocalTime().toString());  // 시간 부분
            }
            return orderDTO;
        }).toList();

        int total = (int) pageOrder.getTotalElements();

        return PageResponseDTO.<OrderDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(orderList)
                .total(total)
                .build();
    }
    public void DeleteOrders(List<String> orderIds) {
        for(String id : orderIds) {
            try{
                int orderId = Integer.parseInt(id);
                orderRepository.deleteById(orderId);
            }catch (NumberFormatException e){
                System.out.println("Invalid Order ID format: " + id);
                throw new IllegalArgumentException("Invalid Order ID format: " + id);
            }
        }
    }
}
