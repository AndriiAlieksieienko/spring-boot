package com.andrii.service;

import com.andrii.dto.order.CreateOrderRequestDto;
import com.andrii.dto.order.OrderDto;
import com.andrii.dto.order.OrderItemDto;
import com.andrii.dto.order.UpdateOrderStatusDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto placeOrder(CreateOrderRequestDto requestDto);

    Page<OrderDto> getOrders(Pageable pageable);

    List<OrderItemDto> getOrderItems(Long orderId);

    OrderItemDto getOrderItem(Long orderId, Long itemId);

    OrderDto updateStatus(Long orderId, UpdateOrderStatusDto requestDto);
}
