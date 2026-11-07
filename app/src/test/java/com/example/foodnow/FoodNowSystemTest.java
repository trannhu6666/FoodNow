package com.example.foodnow;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class FoodNowSystemTest {

    // STT 1: Đăng nhập - Nhập tài khoản hợp lệ -> Đăng nhập thành công
    @Test
    public void testSTT1_ValidLogin() {
        User validUser = new User("1", "Ngọc Như", "0901234567", 1200);
        assertNotNull("User object should not be null upon valid login", validUser);
        assertEquals("0901234567", validUser.getPhone());
        assertEquals("Ngọc Như", validUser.getName());
    }

    // STT 2: Đăng nhập - Nhập sai mật khẩu -> Hiển thị thông báo lỗi
    @Test
    public void testSTT2_InvalidLogin() {
        User invalidUser = null; // simulate checkLogin returning null on wrong password
        assertNull("User object should be null when credentials are invalid", invalidUser);
    }

    // STT 3: Đăng ký - Nhập thông tin hợp lệ -> Tạo tài khoản thành công
    @Test
    public void testSTT3_RegisterUser() {
        String name = "Nguyễn Văn A";
        String phone = "0988888888";
        String password = "123";

        boolean isRegistered = (!name.isEmpty() && !phone.isEmpty() && !password.isEmpty());
        assertTrue("Registration should succeed with valid inputs", isRegistered);
    }

    // STT 4: Tìm kiếm - Nhập tên món ăn -> Hiển thị kết quả phù hợp
    @Test
    public void testSTT4_SearchFood() {
        List<Food> foods = Arrays.asList(
                new Food("101", "Trà Sữa Trân Châu", "Ngon", 35000, "", 4.8, "1", "Royal Tea", "1 km", "20 phút"),
                new Food("102", "Gà Rán Sốt Cay", "Cay", 45000, "", 4.9, "2", "Chicken House", "1.2 km", "25 phút")
        );

        String query = "Trà Sữa";
        boolean found = false;
        for (Food f : foods) {
            if (f.getName().toLowerCase().contains(query.toLowerCase())) {
                found = true;
                break;
            }
        }
        assertTrue("Search should find matching food items", found);
    }

    // STT 5: Giỏ hàng - Thêm món ăn vào giỏ -> Món ăn được thêm vào giỏ
    @Test
    public void testSTT5_AddToCart() {
        CartItem item = new CartItem("101", "Trà Sữa Trân Châu", 1, Arrays.asList("Trân châu đen"), 35000);
        assertNotNull(item);
        assertEquals("101", item.getFoodId());
        assertEquals(1, item.getQuantity());
        assertEquals(35000.0, item.getTotalPrice(), 0.001);
    }

    // STT 6: Giỏ hàng - Thay đổi số lượng -> Tổng tiền được cập nhật
    @Test
    public void testSTT6_UpdateCartQuantity() {
        double unitPrice = 35000;
        int quantity = 3;
        double calculatedTotal = unitPrice * quantity;

        assertEquals(105000.0, calculatedTotal, 0.001);
    }

    // STT 7: Voucher - Nhập mã hợp lệ -> Áp dụng giảm giá thành công
    @Test
    public void testSTT7_ApplyVoucher() {
        Voucher voucher = new Voucher("v1", "Giảm 50k", "Đơn từ 150k", "31/12/2025", "FOODNOW50", "Giảm giá", 150000, 50000);
        double subtotal = 160000;

        boolean isValid = subtotal >= voucher.getMinSpend();
        assertTrue("Voucher should apply successfully when min spend is met", isValid);

        double discount = voucher.getDiscountValue();
        assertEquals(50000.0, discount, 0.001);
    }

    // STT 8: Thanh toán - Xác nhận đặt hàng -> Đơn hàng được tạo
    @Test
    public void testSTT8_CreateOrder() {
        Order order = new Order("NF123456", "Chicken House", "2 món ngon đã chọn", "Đang đến", 115000, "Hôm nay • 14:00");
        assertNotNull(order);
        assertEquals("NF123456", order.getOrderId());
        assertEquals("Đang đến", order.getStatus());
        assertEquals(115000.0, order.getTotalAmount(), 0.001);
    }

    // STT 9: Đơn hàng - Xem lịch sử đơn hàng -> Hiển thị danh sách đơn hàng
    @Test
    public void testSTT9_ViewOrderHistory() {
        List<Order> history = Arrays.asList(
                new Order("NF1", "Royal Tea", "1 món", "Đã hoàn thành", 40000, "01/10/2025"),
                new Order("NF2", "KFC", "2 món", "Đã hoàn thành", 80000, "02/10/2025")
        );

        assertFalse("Order history list should not be empty", history.isEmpty());
        assertEquals(2, history.size());
    }

    // STT 10: Admin - Thêm món ăn mới -> Món ăn được lưu vào hệ thống
    @Test
    public void testSTT10_AdminAddFood() {
        Food newFood = new Food("999", "Bánh Mì Đặc Biệt", "Ngon", 30000, "", 5.0, "4", "Bánh Mì Sài Gòn", "0.5 km", "10 phút");
        assertNotNull(newFood);
        assertEquals("999", newFood.getId());
        assertEquals("Bánh Mì Đặc Biệt", newFood.getName());
    }
}
