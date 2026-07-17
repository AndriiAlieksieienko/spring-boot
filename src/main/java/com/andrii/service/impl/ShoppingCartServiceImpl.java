package com.andrii.service.impl;

import com.andrii.dto.cart.CreateCartItemRequestDto;
import com.andrii.dto.cart.ShoppingCartDto;
import com.andrii.dto.cart.UpdateCartItemDto;
import com.andrii.exception.EntityNotFoundException;
import com.andrii.mapper.CartItemMapper;
import com.andrii.mapper.ShoppingCartMapper;
import com.andrii.model.Book;
import com.andrii.model.CartItem;
import com.andrii.model.ShoppingCart;
import com.andrii.model.User;
import com.andrii.repository.book.BookRepository;
import com.andrii.repository.cart.CartItemRepository;
import com.andrii.repository.cart.ShoppingCartRepository;
import com.andrii.repository.user.UserRepository;
import com.andrii.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getShoppingCart() {
        Long userId = getUserId();
        ShoppingCart cart = getShoppingCartByUserId(userId);
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addBook(CreateCartItemRequestDto requestDto) {
        Long userId = getUserId();
        ShoppingCart cart = getShoppingCartByUserId(userId);

        Book book = bookRepository.findById(requestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find the book by id: " + requestDto.bookId()
                ));

        Optional<CartItem> item = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(book.getId()))
                .findFirst();

        if (item.isPresent()) {
            CartItem cartItem = item.get();
            cartItem.setQuantity(cartItem.getQuantity()
                    + requestDto.quantity());
        } else {
            CartItem newItem = cartItemMapper.toEntity(requestDto);
            newItem.setShoppingCart(cart);
            newItem.setQuantity(requestDto.quantity());
            cart.getCartItems().add(newItem);
        }

        shoppingCartRepository.save(cart);
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateBookQuantity(Long id, UpdateCartItemDto requestDto) {
        Long userId = getUserId();
        ShoppingCart cart = getShoppingCartByUserId(userId);
        CartItem cartItem = getCartItemById(id, cart.getId());
        cartItemMapper.updateCartFromDto(cartItem, requestDto);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Long userId = getUserId();
        ShoppingCart cart = getShoppingCartByUserId(userId);
        CartItem cartItem = getCartItemById(id, cart.getId());

        cart.getCartItems().remove(cartItem);
        shoppingCartRepository.save(cart);
    }

    @Override
    @Transactional
    public ShoppingCart createCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        return shoppingCartRepository.save(cart);
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
                        "Can't find the user by id " + userId
                ));
    }

    private CartItem getCartItemById(Long id, Long cartId) {
        return cartItemRepository.findByIdAndShoppingCartId(id, cartId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find the cart by id: " + cartId
                ));
    }
}
