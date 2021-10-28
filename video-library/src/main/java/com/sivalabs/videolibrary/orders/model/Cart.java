package com.sivalabs.videolibrary.orders.model;

import com.sivalabs.videolibrary.orders.entity.OrderedProduct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Cart {

    @Setter @Getter private List<LineItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(OrderedProduct product) {
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getTmdbId().equals(product.getTmdbId())) {
                lineItem.setQuantity(lineItem.getQuantity() + 1);
                return;
            }
        }
        LineItem item = new LineItem(product, 1);
        this.items.add(item);
    }

    public void updateItemQuantity(OrderedProduct product, int quantity) {
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getTmdbId().equals(product.getTmdbId())) {
                lineItem.setQuantity(quantity);
            }
        }
    }

    public void removeItem(Long tmdbId) {
        LineItem item = null;
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getTmdbId().equals(tmdbId)) {
                item = lineItem;
                break;
            }
        }
        if (item != null) {
            items.remove(item);
        }
    }

    public void clearItems() {
        items = new ArrayList<>();
    }

    public int getItemCount() {
        int count = 0;
        for (LineItem lineItem : items) {
            count += lineItem.getQuantity();
        }
        return count;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0.0");
        for (LineItem lineItem : items) {
            amount = amount.add(lineItem.getSubTotal());
        }
        return amount;
    }
}
