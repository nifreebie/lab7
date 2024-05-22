package org.example.contract.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable, Comparable {
    @JacksonXmlProperty(localName = "id")
    private long id;
    @JacksonXmlProperty(localName = "name")

    private String name;
    @JacksonXmlProperty(localName = "coordinates")

    private Coordinates coordinates;
    @JacksonXmlProperty(localName = "creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @JacksonXmlProperty(localName = "price")
    private int price;
    @JacksonXmlProperty(localName = "partNumber")
    private String partNumber;
    @JacksonXmlProperty(localName = "unitOfMeasure")
    private UnitOfMeasure unitOfMeasure;
    @JacksonXmlProperty(localName = "manufacturer")
    private Organization manufacturer;

    public Product(long id, ProductDTO productDTO) {
        this.id = id;
        this.creationDate = LocalDate.now();
        this.name = productDTO.getName();
        this.coordinates = productDTO.getCoordinates();
        this.price = productDTO.getPrice();
        this.partNumber = productDTO.getPartNumber();
        this.unitOfMeasure = productDTO.getUnitOfMeasure();
        this.manufacturer = productDTO.getManufacturer();

    }

//    public Product(long id, String name, Coordinates coordinates, LocalDate creationDate, int price, String partNumber, UnitOfMeasure unitOfMeasure, Organization manufacturer) {
//        this.id = id;
//        this.name = name;
//        this.coordinates = coordinates;
//        this.creationDate = creationDate;
//        this.price = price;
//        this.partNumber = partNumber;
//        this.unitOfMeasure = unitOfMeasure;
//        this.manufacturer = manufacturer;
//    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", unitOfMeasure=" + unitOfMeasure +
                ", manufacturer=" + manufacturer.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && Objects.equals(name, product.name) && Objects.equals(coordinates, product.coordinates) && Objects.equals(creationDate, product.creationDate) && Objects.equals(partNumber, product.partNumber) && unitOfMeasure == product.unitOfMeasure && Objects.equals(manufacturer, product.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, partNumber, unitOfMeasure, manufacturer);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
