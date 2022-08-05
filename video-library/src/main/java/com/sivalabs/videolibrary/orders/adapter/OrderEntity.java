package com.sivalabs.videolibrary.orders.adapter;

import com.sivalabs.videolibrary.common.entity.BaseEntity;
import com.sivalabs.videolibrary.orders.domain.OrderStatus;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "orders")
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
public class OrderEntity extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "order_id_generator",
            sequenceName = "order_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "order_id_generator")
    private Long id;

    private String orderId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private Set<OrderItemEntity> items;

    private String customerName;

    private String customerEmail;

    private String deliveryAddress;

    private String creditCardNumber;

    private String cvv;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_by")
    private Long createdBy;

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0.0");
        for (OrderItemEntity orderItem : items) {
            amount = amount.add(orderItem.getSubTotal());
        }
        return amount;
    }
}