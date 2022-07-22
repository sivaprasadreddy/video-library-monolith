package com.sivalabs.videolibrary.datafactory;

import static com.sivalabs.videolibrary.common.utils.CommonUtils.toSlug;

import com.sivalabs.videolibrary.catalog.domain.CategoryEntity;
import com.sivalabs.videolibrary.catalog.domain.ProductEntity;
import com.sivalabs.videolibrary.customers.domain.CustomerEntity;
import com.sivalabs.videolibrary.orders.domain.OrderEntity;
import com.sivalabs.videolibrary.orders.domain.OrderItemEntity;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestDataFactory {

    public static ProductEntity createProduct(String title, String... categories) {
        ProductEntity product = new ProductEntity();
        product.setTitle(title);
        product.setCategories(
                Arrays.stream(categories)
                        .map(
                                g -> {
                                    CategoryEntity category = new CategoryEntity();
                                    category.setName(g);
                                    category.setSlug(toSlug(g));
                                    return category;
                                })
                        .collect(Collectors.toSet()));
        return product;
    }

    public static OrderEntity createOrder(Long id) {
        OrderEntity order = new OrderEntity();
        order.setId(id);
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(OrderEntity.OrderStatus.NEW);
        order.setCustomerName("customer 1");
        order.setCustomerEmail("customer1@gmail.com");
        order.setCreditCardNumber("1111111111111");
        order.setCvv("123");
        order.setDeliveryAddress("Hyderabad");

        Set<OrderItemEntity> items = new HashSet<>();
        items.add(createOrderItem(order));
        order.setItems(items);

        CustomerEntity createdBy = new CustomerEntity();
        createdBy.setId(1L);
        order.setCreatedBy(createdBy.getId());
        return order;
    }

    public static OrderItemEntity createOrderItem(OrderEntity order) {
        OrderItemEntity item = new OrderItemEntity();
        item.setProductCode("P001");
        item.setProductName("Some Product");
        item.setProductPrice(BigDecimal.TEN);
        item.setQuantity(1);
        item.setOrder(order);
        return item;
    }
}
