package org.example.server.collection;

import lombok.Getter;
import lombok.Setter;
import org.example.contract.exceptions.UserIsNotOwnerException;
import org.example.contract.model.Product;
import org.example.contract.model.ProductDTO;
import org.example.contract.model.User;
import org.example.server.db.DatabaseConnection;
import org.example.server.utils.ProductComparator;

import java.sql.SQLException;
import java.util.*;
@Getter
@Setter
public class CollectionManager {
    private Set<Product> products;
    private long lastId = 0;
    private DatabaseConnection connection;

    public CollectionManager(Set<Product> productCollection) {
        this.products = productCollection;
    }

    public void add(ProductDTO productDTO, String ownerLogin) {
        try{
            connection.addProduct(productDTO, ownerLogin);
            loadCollectionFromDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadCollectionFromDB() throws SQLException {
        this.products = this.connection.getAllProducts();
    }

    public void removeById(long id, String login ) throws SQLException, UserIsNotOwnerException {
        this.connection.removeById(id, login );
        loadCollectionFromDB();
    }

    public void updateById(long id, ProductDTO productDTO, String ownerLogin) throws SQLException {
        this.connection.updateProduct(id, productDTO, ownerLogin);
        loadCollectionFromDB();
    }

    public long generateId() {
        return ++lastId;
    }
    public long getMaxId(){
        long maxId = -1;
        if(products.isEmpty()){
            return lastId;
        }
        else{
            for(Product p: products){
                if(p.getId() > maxId) maxId = p.getId();
            }
            return maxId;
        }

    }

    public int getSize() {
        return products.size();
    }

    public void sort(Comparator<Product> comparator) {
        List<Product> list = new ArrayList<>();
        for(Product p: products){
            list.add(p);
        }
        list.sort(comparator);
        products = new LinkedHashSet<>(list);

    }

    public boolean isIdExists(long idToFind) {
        boolean flag = false;
        for (Product p : products) {
            if (p.getId() == idToFind) flag = true;
        }
        return flag;

    }

    public boolean checkIfMin(ProductDTO productDTO) {
        ProductComparator productComparator = new ProductComparator();
        sort(productComparator);
        Product p = new Product(generateId(), productDTO);
        List<Product> list = new ArrayList<>(products);
        if (productComparator.compare(p, list.get(0)) < 0) {
            return true;
        } else {
            return false;
        }

    }

    public void removeGreater(ProductDTO productDTO) {
        Product p = new Product(generateId(), productDTO);
        ProductComparator productComparator = new ProductComparator();
        for (Product product : products) {
            if (productComparator.compare(product, p) > 0) {
                products.remove(product);
            }

        }

    }

    public void removeLower(ProductDTO productDTO) {
        Product p = new Product(generateId(), productDTO);
        ProductComparator productComparator = new ProductComparator();
        for (Product product : products) {
            if (productComparator.compare(product, p) < 0) {
                products.remove(product);
            }

        }

    }

    public void clear(String owner) throws SQLException {
        this.connection.clearCollectionForUser(owner);
        loadCollectionFromDB();
    }

    public void save() {
        Storage.save(products,
                "collection");

    }
}
