package com.sivalabs.videolibrary.datafactory;

import static com.sivalabs.videolibrary.common.utils.CommonUtils.toSlug;

import com.sivalabs.videolibrary.catalog.entity.Category;
import com.sivalabs.videolibrary.catalog.entity.Product;
import com.sivalabs.videolibrary.customers.entity.User;
import com.sivalabs.videolibrary.orders.entity.Order;
import com.sivalabs.videolibrary.orders.entity.OrderItem;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestDataFactory {

    public static User createUser() {
        String uuid = UUID.randomUUID().toString();
        return createUser(uuid + "@gmail.com", uuid);
    }

    public static User createUser(String email) {
        String uuid = UUID.randomUUID().toString();
        return createUser(email, uuid);
    }

    public static User createUser(String email, String password) {
        User user = new User();
        user.setName("someuser");
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static Product createProduct(String title, String... categories) {
        Product product = new Product();
        product.setTitle(title);
        product.setCategories(
                Arrays.stream(categories)
                        .map(
                                g -> {
                                    Category category = new Category();
                                    category.setName(g);
                                    category.setSlug(toSlug(g));
                                    return category;
                                })
                        .collect(Collectors.toSet()));
        return product;
    }

    public static Order createOrder(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(Order.OrderStatus.NEW);
        order.setCustomerName("customer 1");
        order.setCustomerEmail("customer1@gmail.com");
        order.setCreditCardNumber("1111111111111");
        order.setCvv("123");
        order.setDeliveryAddress("Hyderabad");

        Set<OrderItem> items = new HashSet<>();
        items.add(createOrderItem(order));
        order.setItems(items);

        User createdBy = new User();
        createdBy.setId(1L);
        order.setCreatedBy(createdBy.getId());
        return order;
    }

    public static OrderItem createOrderItem(Order order) {
        OrderItem item = new OrderItem();
        item.setProductCode("P001");
        item.setProductName("Some Product");
        item.setProductPrice(BigDecimal.TEN);
        item.setQuantity(1);
        item.setOrder(order);
        return item;
    }
}
