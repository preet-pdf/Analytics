package com.example.webdevelopment.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDetails {
    private  int price;
    private  String foodName;
    private  String orderId;
    private  String foodType;
    private  String foodGenre;
    private  int howMuchCaloric;
    private Date orderDate;
}
