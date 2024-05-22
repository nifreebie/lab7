package org.example.server.utils;



import org.example.contract.model.Product;

import java.util.Comparator;

public class PartNumberProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getPartNumber().compareTo(p2.getPartNumber());
    }
}
