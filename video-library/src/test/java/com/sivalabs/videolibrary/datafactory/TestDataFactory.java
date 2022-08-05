package com.sivalabs.videolibrary.datafactory;

import static com.sivalabs.videolibrary.common.utils.CommonUtils.toSlug;

import com.sivalabs.videolibrary.catalog.domain.Category;
import com.sivalabs.videolibrary.catalog.domain.Product;
import com.sivalabs.videolibrary.customers.domain.Customer;
import com.sivalabs.videolibrary.orders.domain.Order;
import com.sivalabs.videolibrary.orders.domain.OrderItem;
import com.sivalabs.videolibrary.orders.domain.OrderStatus;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestDataFactory {

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
        order.setStatus(OrderStatus.NEW);
        order.setCustomerName("customer 1");
        order.setCustomerEmail("customer1@gmail.com");
        order.setCreditCardNumber("1111111111111");
        order.setCvv("123");
        order.setDeliveryAddress("Hyderabad");

        Set<OrderItem> items = new HashSet<>();
        items.add(createOrderItem(order));
        order.setItems(items);

        Customer createdBy = new Customer();
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
        return item;
    }
}
