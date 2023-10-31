package com.shop.controllers;

import com.shop.classes.Administrator;
import com.shop.classes.Customer;
import com.shop.classes.Employee;
import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;

public class MainShopController {
    private User user;
    private Customer customer;
    private Employee employee;
    private Administrator administrator;
    private EntityManagerFactory entityManagerFactory;
    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        if (user.getUserType().equals("Customer")){
            customer = (Customer) user;
        } else if (user.getUserType().equals("Administrator")){
            administrator = (Administrator) user;
        } else {
            employee = (Employee) user;
        }
    }

    public void printUser(){
        System.out.println(user);
    }

    public void printSMT(){
        System.out.println("SMT");
    }
}
