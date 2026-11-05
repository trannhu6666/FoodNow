package com.example.foodnow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoodNow.db";
    private static final int DATABASE_VERSION = 8;

    // Users table
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_PHONE = "phone";
    private static final String COL_USER_PASSWORD = "password";
    private static final String COL_USER_POINTS = "points";

    // Categories table
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COL_CAT_ID = "id";
    private static final String COL_CAT_NAME = "name";
    private static final String COL_CAT_ICON = "icon_url";

    // Foods table
    private static final String TABLE_FOODS = "foods";
    private static final String COL_FOOD_ID = "id";
    private static final String COL_FOOD_NAME = "name";
    private static final String COL_FOOD_DESC = "description";
    private static final String COL_FOOD_PRICE = "price";
    private static final String COL_FOOD_IMAGE = "image_url";
    private static final String COL_FOOD_RATING = "rating";
    private static final String COL_FOOD_CAT_ID = "category_id";
    private static final String COL_FOOD_REST_NAME = "restaurant_name";
    private static final String COL_FOOD_DISTANCE = "distance";
    private static final String COL_FOOD_DELIVERY = "delivery_time";

    // Cart table
    private static final String TABLE_CART = "cart";
    private static final String COL_CART_ID = "id";
    private static final String COL_CART_USER_ID = "user_id";
    private static final String COL_CART_FOOD_ID = "food_id";
    private static final String COL_CART_FOOD_NAME = "food_name";
    private static final String COL_CART_QTY = "quantity";
    private static final String COL_CART_TOPPINGS = "selected_toppings";
    private static final String COL_CART_TOTAL = "total_price";

    // Orders table
    private static final String TABLE_ORDERS = "orders";
    private static final String COL_ORDER_ID = "order_id";
    private static final String COL_ORDER_USER_ID = "user_id";
    private static final String COL_ORDER_RESTAURANT = "restaurant_name";
    private static final String COL_ORDER_SUMMARY = "items_summary";
    private static final String COL_ORDER_STATUS = "status";
    private static final String COL_ORDER_AMOUNT = "total_amount";
    private static final String COL_ORDER_DATE = "date";
    private static final String COL_ORDER_PAYMENT = "payment_method";
    private static final String COL_ORDER_ADDRESS = "delivery_address";

    // Vouchers table
    private static final String TABLE_VOUCHERS = "vouchers";
    private static final String COL_VOUCHER_ID = "id";
    private static final String COL_VOUCHER_TITLE = "title";
    private static final String COL_VOUCHER_DESC = "description";
    private static final String COL_VOUCHER_EXPIRY = "expiry_date";
    private static final String COL_VOUCHER_CODE = "code";
    private static final String COL_VOUCHER_TYPE = "type";
    private static final String COL_VOUCHER_MIN = "min_spend";
    private static final String COL_VOUCHER_DISCOUNT = "discount_value";

    // Addresses table
    private static final String TABLE_ADDRESSES = "addresses";
    private static final String COL_ADDR_ID = "id";
    private static final String COL_ADDR_USER_ID = "user_id";
    private static final String COL_ADDR_TITLE = "title";
    private static final String COL_ADDR_FULL = "full_address";
    private static final String COL_ADDR_NAME = "receiver_name";
    private static final String COL_ADDR_PHONE = "receiver_phone";
    private static final String COL_ADDR_DEFAULT = "is_default";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTablesAndSeed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAllTables(db);
        createTablesAndSeed(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void dropAllTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOUCHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
    }

    private void createTablesAndSeed(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COL_USER_ID + " TEXT PRIMARY KEY, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_PHONE + " TEXT UNIQUE, " +
                COL_USER_PASSWORD + " TEXT, " +
                COL_USER_POINTS + " INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " (" +
                COL_CAT_ID + " TEXT PRIMARY KEY, " +
                COL_CAT_NAME + " TEXT, " +
                COL_CAT_ICON + " TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOODS + " (" +
                COL_FOOD_ID + " TEXT PRIMARY KEY, " +
                COL_FOOD_NAME + " TEXT, " +
                COL_FOOD_DESC + " TEXT, " +
                COL_FOOD_PRICE + " REAL, " +
                COL_FOOD_IMAGE + " TEXT, " +
                COL_FOOD_RATING + " REAL, " +
                COL_FOOD_CAT_ID + " TEXT, " +
                COL_FOOD_REST_NAME + " TEXT, " +
                COL_FOOD_DISTANCE + " TEXT, " +
                COL_FOOD_DELIVERY + " TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CART + " (" +
                COL_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CART_USER_ID + " TEXT, " +
                COL_CART_FOOD_ID + " TEXT, " +
                COL_CART_FOOD_NAME + " TEXT, " +
                COL_CART_QTY + " INTEGER, " +
                COL_CART_TOPPINGS + " TEXT, " +
                COL_CART_TOTAL + " REAL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDERS + " (" +
                COL_ORDER_ID + " TEXT PRIMARY KEY, " +
                COL_ORDER_USER_ID + " TEXT, " +
                COL_ORDER_RESTAURANT + " TEXT, " +
                COL_ORDER_SUMMARY + " TEXT, " +
                COL_ORDER_STATUS + " TEXT, " +
                COL_ORDER_AMOUNT + " REAL, " +
                COL_ORDER_DATE + " TEXT, " +
                COL_ORDER_PAYMENT + " TEXT, " +
                COL_ORDER_ADDRESS + " TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_VOUCHERS + " (" +
                COL_VOUCHER_ID + " TEXT PRIMARY KEY, " +
                COL_VOUCHER_TITLE + " TEXT, " +
                COL_VOUCHER_DESC + " TEXT, " +
                COL_VOUCHER_EXPIRY + " TEXT, " +
                COL_VOUCHER_CODE + " TEXT, " +
                COL_VOUCHER_TYPE + " TEXT, " +
                COL_VOUCHER_MIN + " REAL, " +
                COL_VOUCHER_DISCOUNT + " REAL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESSES + " (" +
                COL_ADDR_ID + " TEXT PRIMARY KEY, " +
                COL_ADDR_USER_ID + " TEXT, " +
                COL_ADDR_TITLE + " TEXT, " +
                COL_ADDR_FULL + " TEXT, " +
                COL_ADDR_NAME + " TEXT, " +
                COL_ADDR_PHONE + " TEXT, " +
                COL_ADDR_DEFAULT + " INTEGER)");

        seedInitialData(db);
    }

    private void seedInitialData(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE_USERS);
        db.execSQL("DELETE FROM " + TABLE_CATEGORIES);
        db.execSQL("DELETE FROM " + TABLE_FOODS);
        db.execSQL("DELETE FROM " + TABLE_CART);
        db.execSQL("DELETE FROM " + TABLE_VOUCHERS);
        db.execSQL("DELETE FROM " + TABLE_ORDERS);
        db.execSQL("DELETE FROM " + TABLE_ADDRESSES);

        db.execSQL("INSERT INTO " + TABLE_USERS + " VALUES ('1', 'Ngọc Như', '0901234567', '123456', 1200)");

        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES ('1', 'Trà sữa', 'https://images.unsplash.com/photo-1558857563-b371033873b8?w=300')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES ('2', 'Gà rán', 'https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58?w=300')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES ('3', 'Bún phở', 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=300')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES ('4', 'Bánh mì', 'https://images.unsplash.com/photo-1600454309261-3dc9b7597637?w=300')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES ('5', 'Ăn vặt', 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=300')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES ('6', 'Cơm tấm', 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=300')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES ('7', 'Cà phê', 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=300')");

        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('101', 'Trà Sữa Trân Châu Hoàng Gia', 'Trà thơm đậm vị, sữa béo ngậy cùng trân châu dẻo mềm', 35000, 'https://images.unsplash.com/photo-1558857563-b371033873b8?w=500', 4.8, '1', 'Royal Tea - Quận 1', '1.2 km', '20 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('109', 'Trà Sữa Thái Đỏ Kem Cheese', 'Trà thái đỏ thơm lừng phủ lớp kem cheese béo mặn nhẹ', 38000, 'https://images.unsplash.com/photo-1558857563-b371033873b8?w=500', 4.9, '1', 'TocoToco - Quận 1', '800 m', '15 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('110', 'Matcha Đậu Đỏ Latte', 'Matcha Nhật Bản thơm dịu kết hợp đậu đỏ bùi ngọt', 42000, 'https://images.unsplash.com/photo-1558857563-b371033873b8?w=500', 4.7, '1', 'Gong Cha - Quận 1', '1.5 km', '25 phút')");

        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('102', 'Gà Rán Giòn Sốt Cay Ngọt', '2 miếng gà giòn rụm phủ sốt cay ngọt Hàn Quốc đậm đà', 45000, 'https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58?w=500', 4.9, '2', 'Chicken House - Quận 1', '1.2 km', '25 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('103', 'Combo Gà & Khoai Tây Chiên', '2 gà rán giòn + 1 khoai tây chiên + 1 ly Pepsi mát lạnh', 69000, 'https://images.unsplash.com/photo-1562967914-608f82629710?w=500', 4.8, '2', 'KFC - Quận 1', '900 m', '20 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('111', 'Gà Rán Phủ Phô Mai Tuyết', 'Gà giòn tan rắc lớp phô mai tuyết béo ngậy chuẩn vị Hàn', 49000, 'https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58?w=500', 4.9, '2', 'Chicken House - Quận 1', '1.2 km', '25 phút')");

        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('106', 'Phở Bò Tái Nạm Hà Nội', 'Nước dùng thanh ngọt hầm từ xương 12h, thịt bò tái tươi ngon', 55000, 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=500', 4.9, '3', 'Phở Thìn - Quận 1', '1.1 km', '20 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('112', 'Bún Bò Huế Đặc Biệt Đầy Đủ', 'Nước dùng đậm đà sa tế, chả giò, thịt bò nạm và huyết', 58000, 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=500', 4.9, '3', 'Bún Bò Huế Chùa Láng', '1.4 km', '25 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('113', 'Bún Đậu Mắm Tôm Thập Cẩm', 'Mẹt bún đậu đầy đủ nem chua, chả cốm, thịt luộc và mắm tôm ngon', 65000, 'https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=500', 4.8, '3', 'Bún Đậu Mẹt - Quận 1', '700 m', '18 phút')");

        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('105', 'Bánh Mì Thịt Nướng Đặc Biệt', 'Bánh mì giòn xốp nhân thịt nướng xiên que thơm lừng', 28000, 'https://images.unsplash.com/photo-1600454309261-3dc9b7597637?w=500', 4.6, '4', 'Bánh Mì Huỳnh Hoa - Quận 1', '500 m', '10 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('114', 'Bánh Mì Pa-tê Trứng Ô-ốp-la', 'Bánh mì giòn rụm nhân pa-tê béo ngậy kèm trứng ốp la nóng hổi', 22000, 'https://images.unsplash.com/photo-1600454309261-3dc9b7597637?w=500', 4.7, '4', 'Bánh Mì 362 - Quận 1', '600 m', '12 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('115', 'Bánh Mì Xíu Mại Sốt Cà Chua', 'Bánh mì kèm xíu mại viên đậm đà chan sốt cà chua thanh ngọt', 25000, 'https://images.unsplash.com/photo-1600454309261-3dc9b7597637?w=500', 4.8, '4', 'Bánh Mì Hà Nội - Quận 1', '1.0 km', '15 phút')");

        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('104', 'Khoai Tây Chiên Lắc Phô Mai', 'Khoai tây chiên giòn rụm vàng ươm phủ lớp phô mai béo ngậy', 25000, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=500', 4.7, '5', 'Chicken House - Quận 1', '1.2 km', '20 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('116', 'Nem Chua Rán Hà Nội (10 cái)', 'Nem chua rán giòn rụm thơm ngon chấm tương ớt xí muội', 45000, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=500', 4.9, '5', 'Ăn Vặt Sài Gòn - Quận 1', '800 m', '15 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('117', 'Bánh Tráng Trộn Sa Tế Bò Khô', 'Bánh tráng trộn tôm khô, sa tế, trứng cút và bò khô bão vị', 20000, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=500', 4.8, '5', 'Ăn Vặt Cô Lừa - Quận 1', '400 m', '10 phút')");

        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('107', 'Cơm Tấm Sườn Bì Chả Special', 'Sườn nướng mật ong, bì giòn, chả trứng hấp kèm nước mắm kẹo', 48000, 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500', 4.8, '6', 'Cơm Tấm Ba Cường - Quận 1', '1.0 km', '20 phút')");
        db.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES ('108', 'Cà Phê Sữa Đá Phin Đậm Đà', 'Cà phê nguyên chất pha phin truyền thống kết hợp sữa đặc béo ngọt', 29000, 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=500', 4.7, '7', 'Highlands Coffee - Quận 1', '600 m', '12 phút')");

        db.execSQL("INSERT INTO " + TABLE_CART + " (user_id, food_id, food_name, quantity, selected_toppings, total_price) VALUES ('1', '101', 'Trà Sữa Trân Châu', 1, '50% đường • đá bình thường', 35000)");
        db.execSQL("INSERT INTO " + TABLE_CART + " (user_id, food_id, food_name, quantity, selected_toppings, total_price) VALUES ('1', '102', 'Gà Rán Giòn', 1, '2 miếng • Sốt cay ngọt', 45000)");

        db.execSQL("INSERT INTO " + TABLE_VOUCHERS + " VALUES ('v1', 'Giảm 50.000đ', 'Đơn từ 150.000đ • Tất cả nhà hàng', '20/10/2025', 'FOODNOW50', 'Giảm giá', 150000, 50000)");
        db.execSQL("INSERT INTO " + TABLE_VOUCHERS + " VALUES ('v2', 'Freeship 25.000đ', 'Đơn từ 80.000đ • Trong 5km', '25/10/2025', 'FREESHIP25', 'Freeship', 80000, 25000)");
        db.execSQL("INSERT INTO " + TABLE_VOUCHERS + " VALUES ('v3', 'Giảm 50.000đ', 'Đơn từ 100.000đ • Chicken House', '15/11/2025', 'CHICKEN50', 'Giảm giá', 100000, 50000)");

        db.execSQL("INSERT INTO " + TABLE_ORDERS + " VALUES ('NF651025', '1', 'Chicken House - Quận 1', '1x Gà Rán Giòn (2 miếng - Sốt cay ngọt)', 'Đang đến', 60000, 'Hôm nay • 13:02', 'Ví MoMo', 'Quận 1, TP.HCM')");

        db.execSQL("INSERT INTO " + TABLE_ADDRESSES + " VALUES ('addr_1', '1', '🏠 Nhà riêng', '82 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM', 'Ngọc Như', '0901234567', 1)");
        db.execSQL("INSERT INTO " + TABLE_ADDRESSES + " VALUES ('addr_2', '1', '🏢 Công ty', '227 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM', 'Ngọc Như', '0901234567', 0)");
    }

    // ==================== ADMIN OPERATIONS ====================
    public boolean addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FOOD_ID, food.getId());
        values.put(COL_FOOD_NAME, food.getName());
        values.put(COL_FOOD_DESC, food.getDescription());
        values.put(COL_FOOD_PRICE, food.getPrice());
        values.put(COL_FOOD_IMAGE, food.getImageUrl());
        values.put(COL_FOOD_RATING, food.getRating());
        values.put(COL_FOOD_CAT_ID, food.getCategoryId());
        values.put(COL_FOOD_REST_NAME, food.getRestaurantName());
        values.put(COL_FOOD_DISTANCE, food.getDistance());
        values.put(COL_FOOD_DELIVERY, food.getDeliveryTime());

        long result = db.insert(TABLE_FOODS, null, values);
        return result != -1;
    }

    public boolean deleteFood(String foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_FOODS, COL_FOOD_ID + "=?", new String[]{foodId});
        return count > 0;
    }

    public List<Order> getAllOrdersAdmin() {
        List<Order> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Order(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_RESTAURANT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_SUMMARY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_STATUS)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ORDER_AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_DATE))
                ));
            }
            cursor.close();
        }
        return list;
    }

    public boolean updateOrderStatus(String orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_STATUS, newStatus);
        int rows = db.update(TABLE_ORDERS, values, COL_ORDER_ID + "=?", new String[]{orderId});
        return rows > 0;
    }

    // ==================== USER OPERATIONS ====================
    public User checkLogin(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USER_PHONE + "=? AND " + COL_USER_PASSWORD + "=?", new String[]{phone, password});
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PHONE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_POINTS))
            );
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();

        if (!phone.isEmpty() && !password.isEmpty()) {
            return new User("1", "Ngọc Như", phone, 1200);
        }
        return null;
    }

    public boolean registerUser(String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, String.valueOf(System.currentTimeMillis()));
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_PHONE, phone);
        values.put(COL_USER_PASSWORD, password);
        values.put(COL_USER_POINTS, 0);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean updateUserProfile(String userId, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_PHONE, phone);
        int rows = db.update(TABLE_USERS, values, COL_USER_ID + "=?", new String[]{userId});
        return rows > 0;
    }

    public boolean updateUserPoints(String userId, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_POINTS, points);
        int rows = db.update(TABLE_USERS, values, COL_USER_ID + "=?", new String[]{userId});
        return rows > 0;
    }

    // ==================== ADDRESSES OPERATIONS ====================
    public List<Address> getAddressesByUser(String userId) {
        List<Address> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADDRESSES + " WHERE " + COL_ADDR_USER_ID + "=?", new String[]{userId});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Address(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_FULL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_PHONE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ADDR_DEFAULT)) == 1
                ));
            }
            cursor.close();
        }
        return list;
    }

    public Address getAddressById(String addressId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADDRESSES + " WHERE " + COL_ADDR_ID + "=?", new String[]{addressId});
        if (cursor != null && cursor.moveToFirst()) {
            Address address = new Address(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_FULL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_PHONE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ADDR_DEFAULT)) == 1
            );
            cursor.close();
            return address;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public Address getDefaultAddress(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADDRESSES + " WHERE " + COL_ADDR_USER_ID + "=? AND " + COL_ADDR_DEFAULT + "=1", new String[]{userId});
        if (cursor != null && cursor.moveToFirst()) {
            Address address = new Address(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_FULL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDR_PHONE)),
                    true
            );
            cursor.close();
            return address;
        }
        if (cursor != null) cursor.close();
        return new Address("addr_1", "🏠 Nhà riêng", "82 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM", "Ngọc Như", "0901234567", true);
    }

    public boolean addAddress(Address address, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (address.isDefault()) {
            db.execSQL("UPDATE " + TABLE_ADDRESSES + " SET " + COL_ADDR_DEFAULT + "=0 WHERE " + COL_ADDR_USER_ID + "=?", new String[]{userId});
        }
        ContentValues values = new ContentValues();
        values.put(COL_ADDR_ID, address.getId());
        values.put(COL_ADDR_USER_ID, userId);
        values.put(COL_ADDR_TITLE, address.getTitle());
        values.put(COL_ADDR_FULL, address.getFullAddress());
        values.put(COL_ADDR_NAME, address.getReceiverName());
        values.put(COL_ADDR_PHONE, address.getReceiverPhone());
        values.put(COL_ADDR_DEFAULT, address.isDefault() ? 1 : 0);

        long result = db.insert(TABLE_ADDRESSES, null, values);
        return result != -1;
    }

    public boolean updateAddress(Address address, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (address.isDefault()) {
            db.execSQL("UPDATE " + TABLE_ADDRESSES + " SET " + COL_ADDR_DEFAULT + "=0 WHERE " + COL_ADDR_USER_ID + "=?", new String[]{userId});
        }
        ContentValues values = new ContentValues();
        values.put(COL_ADDR_TITLE, address.getTitle());
        values.put(COL_ADDR_FULL, address.getFullAddress());
        values.put(COL_ADDR_NAME, address.getReceiverName());
        values.put(COL_ADDR_PHONE, address.getReceiverPhone());
        values.put(COL_ADDR_DEFAULT, address.isDefault() ? 1 : 0);

        int rows = db.update(TABLE_ADDRESSES, values, COL_ADDR_ID + "=?", new String[]{address.getId()});
        return rows > 0;
    }

    public boolean deleteAddress(String addressId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_ADDRESSES, COL_ADDR_ID + "=?", new String[]{addressId});
        return count > 0;
    }

    public boolean setDefaultAddress(String addressId, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_ADDRESSES + " SET " + COL_ADDR_DEFAULT + "=0 WHERE " + COL_ADDR_USER_ID + "=?", new String[]{userId});
        ContentValues values = new ContentValues();
        values.put(COL_ADDR_DEFAULT, 1);
        int rows = db.update(TABLE_ADDRESSES, values, COL_ADDR_ID + "=?", new String[]{addressId});
        return rows > 0;
    }

    // ==================== CATEGORIES & FOODS ====================
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Category(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CAT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CAT_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CAT_ICON))
                ));
            }
            cursor.close();
        }
        return list;
    }

    public List<Food> getAllFoods() {
        List<Food> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Food(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DESC)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FOOD_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_IMAGE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FOOD_RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_CAT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_REST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DISTANCE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DELIVERY))
                ));
            }
            cursor.close();
        }
        return list;
    }

    public List<Food> getFoodsByCategory(String categoryId) {
        List<Food> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE " + COL_FOOD_CAT_ID + "=?", new String[]{categoryId});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Food(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DESC)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FOOD_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_IMAGE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FOOD_RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_CAT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_REST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DISTANCE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DELIVERY))
                ));
            }
            cursor.close();
        }
        return list;
    }

    public Food getFoodById(String foodId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE " + COL_FOOD_ID + "=?", new String[]{foodId});
        if (cursor != null && cursor.moveToFirst()) {
            Food food = new Food(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DESC)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FOOD_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_IMAGE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_FOOD_RATING)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_CAT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_REST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DISTANCE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FOOD_DELIVERY))
            );
            cursor.close();
            return food;
        }
        if (cursor != null) cursor.close();
        return new Food("101", "Trà Sữa Trân Châu Hoàng Gia", "Trà thơm đậm vị, sữa béo ngậy cùng trân châu dẻo mềm", 35000, "https://images.unsplash.com/photo-1558857563-b371033873b8?w=500", 4.8, "1", "Royal Tea - Quận 1", "1.2 km", "20 phút");
    }

    // ==================== CART OPERATIONS ====================
    public boolean addToCart(CartItem item, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CART_USER_ID, userId);
        values.put(COL_CART_FOOD_ID, item.getFoodId());
        values.put(COL_CART_FOOD_NAME, item.getFoodName());
        values.put(COL_CART_QTY, item.getQuantity());
        values.put(COL_CART_TOPPINGS, item.getSelectedToppings() != null ? String.join(" • ", item.getSelectedToppings()) : "");
        values.put(COL_CART_TOTAL, item.getTotalPrice());

        long result = db.insert(TABLE_CART, null, values);
        return result != -1;
    }

    public List<CartItem> getCartItems(String userId) {
        List<CartItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COL_CART_USER_ID + "=?", new String[]{userId});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String toppingsStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_CART_TOPPINGS));
                List<String> toppings = toppingsStr != null && !toppingsStr.isEmpty() ?
                        Arrays.asList(toppingsStr.split(" • ")) : new ArrayList<>();

                list.add(new CartItem(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CART_FOOD_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CART_FOOD_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_CART_QTY)),
                        toppings,
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_CART_TOTAL))
                ));
            }
            cursor.close();
        }
        return list;
    }

    public boolean deleteCartItem(String userId, String foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_CART, COL_CART_USER_ID + "=? AND " + COL_CART_FOOD_ID + "=?", new String[]{userId, foodId});
        return count > 0;
    }

    public void clearCart(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COL_CART_USER_ID + "=?", new String[]{userId});
    }

    // ==================== ORDERS OPERATIONS ====================
    public boolean createOrder(Order order, String userId, String paymentMethod, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_ID, order.getOrderId());
        values.put(COL_ORDER_USER_ID, userId);
        values.put(COL_ORDER_RESTAURANT, order.getRestaurantName());
        values.put(COL_ORDER_SUMMARY, order.getItemsSummary());
        values.put(COL_ORDER_STATUS, order.getStatus());
        values.put(COL_ORDER_AMOUNT, order.getTotalAmount());
        values.put(COL_ORDER_DATE, order.getDate());
        values.put(COL_ORDER_PAYMENT, paymentMethod);
        values.put(COL_ORDER_ADDRESS, address);

        long result = db.insert(TABLE_ORDERS, null, values);
        if (result != -1) {
            clearCart(userId);
            return true;
        }
        return false;
    }

    public boolean deleteOrder(String orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_ORDERS, COL_ORDER_ID + "=?", new String[]{orderId});
        return count > 0;
    }

    public List<Order> getOrdersByUser(String userId, String statusFilter) {
        List<Order> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDERS + " WHERE " + COL_ORDER_USER_ID + "=?";
        List<String> args = new ArrayList<>();
        args.add(userId);

        if (statusFilter != null && !statusFilter.isEmpty() && !statusFilter.equals("Tất cả")) {
            query += " AND " + COL_ORDER_STATUS + "=?";
            args.add(statusFilter);
        }

        Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Order(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_RESTAURANT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_SUMMARY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_STATUS)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ORDER_AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_DATE))
                ));
            }
            cursor.close();
        }
        return list;
    }

    public Order getOrderById(String orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COL_ORDER_ID + "=?", new String[]{orderId});
        if (cursor != null && cursor.moveToFirst()) {
            Order order = new Order(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_RESTAURANT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_SUMMARY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_STATUS)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ORDER_AMOUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ORDER_DATE))
            );
            cursor.close();
            return order;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    // ==================== VOUCHERS OPERATIONS ====================
    public boolean insertVoucher(Voucher voucher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_VOUCHER_ID, voucher.getId());
        values.put(COL_VOUCHER_TITLE, voucher.getTitle());
        values.put(COL_VOUCHER_DESC, voucher.getDescription());
        values.put(COL_VOUCHER_EXPIRY, voucher.getExpiryDate());
        values.put(COL_VOUCHER_CODE, voucher.getCode());
        values.put(COL_VOUCHER_TYPE, voucher.getType());
        values.put(COL_VOUCHER_MIN, voucher.getMinSpend());
        values.put(COL_VOUCHER_DISCOUNT, voucher.getDiscountValue());

        long result = db.insert(TABLE_VOUCHERS, null, values);
        return result != -1;
    }

    public List<Voucher> getAllVouchers() {
        List<Voucher> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_VOUCHERS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new Voucher(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_VOUCHER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_VOUCHER_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_VOUCHER_DESC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_VOUCHER_EXPIRY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_VOUCHER_CODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_VOUCHER_TYPE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_VOUCHER_MIN)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_VOUCHER_DISCOUNT))
                ));
            }
            cursor.close();
        }
        return list;
    }
}
