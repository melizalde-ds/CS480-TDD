package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Item {
    private String name;
    private String description;
    private double price;
}
