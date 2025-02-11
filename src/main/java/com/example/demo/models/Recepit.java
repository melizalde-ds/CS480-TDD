package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

import java.util.Hashtable;

@Builder
@Data
public class Recepit {
    private int id;
    private Hashtable<Item, Integer> cart;
    private User user;
    private double total;
    private String date;
}
