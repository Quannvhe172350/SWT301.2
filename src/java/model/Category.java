/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author TGDD
 */
public class Category implements Serializable{

    private int category_id;
    private String category_name;
    private ArrayList<Product> product = new ArrayList<>();

    public Category() {
    }
    public Category(String category_name) {
        this.category_name = category_name;
    }

    public Category(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public Category(int category_id) {
        this.category_id = category_id;
    }
    
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Category{" + "category_id=" + category_id + ", category_name=" + category_name +'}';
    }
    

}
