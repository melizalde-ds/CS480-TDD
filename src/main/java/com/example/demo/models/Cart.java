package com.example.demo.models;

import lombok.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Data
@Builder
public class Cart {
    private int id;
    private final Hashtable<Item, Integer> items = new Hashtable<>();
    private double total;

    public void addItem(Item item) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + 1);
        } else {
            items.put(item, 1);
        }
        total += item.getPrice();
    }

    public void removeItem(Item item) {
        if (items.containsKey(item)) {
            total -= (item.getPrice() * items.get(item));
            items.remove(item);
        }
    }

    public void dropCart() {
        items.clear();
        total = 0;
    }

    public Recepit checkout(User user) {
        Recepit recepit = Recepit.builder().id(1).user(user).total(total).date("2025-02-10").build();
        recepit.setCart(new Hashtable<>(items));
        dropCart();
        return recepit;
    }

    public void changeQty(Item item, int qty) {
        if (qty < 0) {
            return;
        }
        if (items.containsKey(item)) {
            if (qty == 0) {
                total -= item.getPrice() * items.get(item);
                items.remove(item);
                return;
            }
            total -= item.getPrice() * items.get(item);
            items.put(item, qty);
            total += item.getPrice() * qty;
        }
    }

    public int getQty(Item item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        return -1;
    }
}
