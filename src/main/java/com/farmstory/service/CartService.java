package com.farmstory.service;

import com.farmstory.dto.*;
import com.farmstory.entity.Cart;
import com.farmstory.entity.Product;
import com.farmstory.repository.market.CartRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public void insertCart(CartDTO cartDTO) {

        Cart cart = modelMapper.map(cartDTO, Cart.class);
        cartRepository.save(cart);
    }

    public CartDTO selectCart(int cartNo) {
        Cart cart = cartRepository.findCartByCartNo(cartNo);
        return modelMapper.map(cart, CartDTO.class);
    }

    public List<CartDTO> selectCartAll(String userId) {
        List<Cart> carts = cartRepository.findCartByUserId(userId);

        return carts.stream().map(Cart::toDTO).toList();
    }
    public void updateCart(Cart cart) {}
    public void deleteCart(int cartNo) {
        cartRepository.deleteById(cartNo);
    }

}
