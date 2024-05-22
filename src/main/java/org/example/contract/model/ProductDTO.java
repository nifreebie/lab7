package org.example.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private String name;
    private Coordinates coordinates;
    private int price;
    private String partNumber;
    private UnitOfMeasure unitOfMeasure;
    private Organization manufacturer;

}
