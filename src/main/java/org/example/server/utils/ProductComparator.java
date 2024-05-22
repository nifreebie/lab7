package org.example.server.utils;



import org.example.contract.model.Product;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        long value1 = p1.getId() * p1.getPrice();
        long value2 = p2.getId() * p2.getPrice();
        if (value1 == value2) {
            return 0;
        }
        if (value1 > value2) {
            return 1;
        } else {
            return -1;
        }

    }
}
