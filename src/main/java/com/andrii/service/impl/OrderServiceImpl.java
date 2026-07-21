package com.andrii.service.impl;

import com.andrii.dto.order.CreateOrderRequestDto;
import com.andrii.dto.order.OrderDto;
import com.andrii.dto.order.OrderItemDto;
import com.andrii.dto.order.UpdateOrderStatusDto;
import com.andrii.exception.OrderProcessingException;
import com.andrii.mapper.OrderItemMapper;
import com.andrii.mapper.OrderMapper;
import com.andrii.model.CartItem;
import com.andrii.model.Order;
import com.andrii.model.OrderItem;
import com.andrii.model.ShoppingCart;
import com.andrii.model.Status;
import com.andrii.model.User;
import com.andrii.repository.cart.ShoppingCartRepository;
import com.andrii.repository.order.OrderItemRepository;
import com.andrii.repository.order.OrderRepository;
import com.andrii.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto placeOrder(CreateOrderRequestDto requestDto) {
        Long userId = getUserId();
        ShoppingCart cart = getShoppingCartByUserId(userId);

        if (cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Shopping cart is empty by userId " + userId);
        }

        Order order = createOrder(cart, requestDto);
        Order savedOrder = orderRepository.save(order);
        cart.getCartItems().clear();

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getOrders(Pageable pageable) {
        Long userId = getUserId();

        return orderRepository
                .findAllByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDto> getOrderItems(Long orderId) {
        Long userId = getUserId();
        Order order = orderRepository
                .findByIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order by id " + orderId)
                );

        return order.getOrderItems()
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemDto getOrderItem(
            Long orderId,
            Long itemId
    ) {
        Long userId = getUserId();
        OrderItem orderItem = orderItemRepository
                .findByIdAndOrderIdAndOrderUserId(itemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order item by id " + itemId)
                );

        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderDto updateStatus(Long orderId, UpdateOrderStatusDto requestDto) {
        Order order = getOrder(orderId);
        order.setStatus(requestDto.status());

        return orderMapper.toDto(orderRepository.save(order));
    }

    private Order getOrder(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find order by id " + id
                ));
    }

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user.getId();
    }

    private ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository
                .findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find the user by id " + userId)
                );
    }

    private Order createOrder(
            ShoppingCart cart,
            CreateOrderRequestDto requestDto
    ) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());

            BigDecimal itemPrice = cartItem.getBook().getPrice();

            orderItem.setPrice(itemPrice);

            total = total.add(
                    itemPrice.multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    )
            );

            order.getOrderItems().add(orderItem);
        }

        order.setTotal(total);
        return order;
    }
}
