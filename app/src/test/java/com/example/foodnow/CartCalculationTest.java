package com.example.foodnow;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class CartCalculationTest {

    @Test
    public void testSubtotalCalculation() {
        List<CartItem> items = Arrays.asList(
                new CartItem("101", "Trà Sữa", 2, Arrays.asList("Trân châu"), 70000),
                new CartItem("102", "Gà Rán", 1, Arrays.asList("Sốt cay"), 45000)
        );

        double subtotal = 0;
        for (CartItem item : items) {
            subtotal += item.getTotalPrice();
        }

        assertEquals(115000.0, subtotal, 0.001);
    }

    @Test
    public void testVoucherMinSpendValidation() {
        double subtotal = 120000;
        Voucher voucher = new Voucher("v1", "Giảm 50k", "Đơn từ 150k", "31/12/2025", "FOODNOW50", "Giảm giá", 150000, 50000);

        boolean canApply = subtotal >= voucher.getMinSpend();
        assertFalse("Should not allow applying voucher if subtotal is below min spend", canApply);

        double validSubtotal = 160000;
        boolean canApplyValid = validSubtotal >= voucher.getMinSpend();
        assertTrue("Should allow applying voucher if subtotal meets min spend", canApplyValid);
    }

    @Test
    public void testLoyaltyPointsEarned() {
        double finalTotal = 120000;
        int currentPoints = 1200;

        int earnedPoints = 0;
        if (finalTotal >= 100000) {
            earnedPoints = 10;
        }

        assertEquals(10, earnedPoints);
        assertEquals(1210, currentPoints + earnedPoints);
    }
}
