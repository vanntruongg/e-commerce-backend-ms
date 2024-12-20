package com.vantruong.cart.viewmodel;

import java.util.List;

public record CartItemDeleteVm(
        String email,
        List<CartItemVm> cartItemVms
) {
}
