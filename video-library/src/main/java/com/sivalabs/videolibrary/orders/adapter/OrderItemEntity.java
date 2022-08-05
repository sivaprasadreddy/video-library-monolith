package com.sivalabs.videolibrary.orders.adapter;

import com.sivalabs.videolibrary.common.entity.BaseEntity;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "order_items")
@EqualsAndHashCode(
        exclude = {"order"},
        callSuper = false)
public class OrderItemEntity extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "order_item_id_generator",
            sequenceName = "order_item_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "order_item_id_generator")
    private Long id;

    private String productCode;

    private String productName;

    private BigDecimal productPrice;

    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public BigDecimal getSubTotal() {
        return productPrice.multiply(new BigDecimal(quantity));
    }
}
